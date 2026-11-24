const messageElement = document.querySelector("#message");
const playerNameElement = document.querySelector("#player-name");
const lobbyPanel = document.querySelector("#lobby-panel");
const roomPanel = document.querySelector("#room-panel");
const finishedPanel = document.querySelector("#finished-panel");
const finishedResult = document.querySelector("#finished-result");
const rematchStatus = document.querySelector("#rematch-status");
const rematchButton = document.querySelector("#rematch-button");
const leaveFinishedRoomButton = document.querySelector("#leave-finished-room-button");
const roomCodeElement = document.querySelector("#room-code");
const roomStatusElement = document.querySelector("#room-status");
const memberListElement = document.querySelector("#member-list");
const readyButton = document.querySelector("#ready-button");
const historyList = document.querySelector("#history-list");
const historySummary = document.querySelector("#history-summary");
const refreshHistoryButton = document.querySelector("#refresh-history-button");
let csrfToken = "";
let currentPlayer = null;
let currentRoom = null;
let finishedMatch = null;
let isStartingMatch = false;

function showMessage(message, isError = false) {
    messageElement.textContent = message;
    messageElement.classList.toggle("error", isError);
}

async function loadCsrfToken(forceRefresh = false) {
    if (csrfToken && !forceRefresh) return csrfToken;
    const response = await fetch("/api/auth/csrf", { credentials: "same-origin" });
    if (!response.ok) throw new Error("Không thể tạo phiên bảo mật.");
    const body = await response.json();
    if (!body.token) throw new Error("Không nhận được token bảo mật.");
    csrfToken = body.token;
    return csrfToken;
}

async function request(url, options = {}) {
    const method = options.method || "GET";
    const headers = { ...(options.headers || {}) };
    if (method !== "GET") {
        await loadCsrfToken();
        headers["X-CSRF-TOKEN"] = csrfToken;
        headers["Content-Type"] = "application/json";
    }
    const response = await fetch(url, { ...options, method, headers, credentials: "same-origin" });
    const body = response.status === 204 ? null : await response.json().catch(() => null);
    if (response.status === 401) {
        window.location.replace("/");
        throw new Error("Phiên đăng nhập đã hết hạn.");
    }
    if (!response.ok) {
        const error = new Error(body?.message || "Yêu cầu không thành công.");
        error.code = body?.code;
        throw error;
    }
    return body;
}

function renderRoom(room) {
    currentRoom = room;
    const hasRoom = room !== null;
    lobbyPanel.classList.toggle("hidden", hasRoom);
    roomPanel.classList.toggle("hidden", !hasRoom);
    finishedPanel.classList.add("hidden");
    if (!hasRoom) return;

    roomCodeElement.textContent = room.roomCode;
    roomStatusElement.textContent = room.status === "WAITING" ? "Đang chờ đủ hai người chơi." : room.status;
    memberListElement.replaceChildren();
    for (const member of room.members) {
        const item = document.createElement("li");
        const role = member.userId === room.ownerId ? " · Chủ phòng" : "";
        const ready = member.ready ? "Sẵn sàng" : "Chưa sẵn sàng";
        item.textContent = `Vị trí ${member.slot}: ${member.displayName}${role} — ${ready}`;
        memberListElement.append(item);
    }
    const ownMember = room.members.find((member) => member.userId === currentPlayer.userId);
    readyButton.textContent = ownMember?.ready ? "Bỏ sẵn sàng" : "Sẵn sàng";
    void startMatchIfEligible(room);
}

function renderFinishedRoom(room, match) {
    currentRoom = room;
    finishedMatch = match;
    lobbyPanel.classList.add("hidden");
    roomPanel.classList.add("hidden");
    finishedPanel.classList.remove("hidden");
    finishedResult.textContent = match.winnerSlot === 1
        ? "Người chơi ở vị trí 1 đã chiến thắng."
        : "Người chơi ở vị trí 2 đã chiến thắng.";
    rematchStatus.textContent = match.rematchVotes === 0
        ? "Cần cả hai người chơi đồng ý để bắt đầu ván mới."
        : `Đã có ${match.rematchVotes}/2 người đồng ý chơi lại.`;
    rematchButton.disabled = false;
}

async function startMatchIfEligible(room) {
    const hasTwoPlayers = room.members.length === 2;
    if (isStartingMatch || room.ownerId !== currentPlayer.userId || !hasTwoPlayers) return;

    isStartingMatch = true;
    try {
        showMessage("Đủ hai người chơi. Đang bắt đầu trận...");
        await request("/api/game/start", { method: "POST", body: "{}" });
        window.location.replace("/game.html");
    } catch (error) {
        showMessage(error.message, true);
        isStartingMatch = false;
    }
}

async function loadRoom() {
    const room = await request("/api/rooms/current");
    currentRoom = room;
    if (room?.status === "PLAYING") {
        let activeMatch;
        try {
            activeMatch = await request("/api/game/current");
        } catch (error) {
            if (error.code === "MATCH_UNAVAILABLE") {
                renderRoom({ ...room, status: "WAITING" });
                showMessage("Server vừa khởi động lại. Phòng đã trở về trạng thái chờ.");
                return;
            }
            throw error;
        }
        if (!activeMatch.finished) {
            window.location.replace("/game.html");
            return;
        }
        renderFinishedRoom(room, activeMatch);
        return;
    }
    renderRoom(room);
}

document.querySelector("#create-room-button").addEventListener("click", async function () {
    try {
        showMessage("Đang tạo phòng...");
        renderRoom(await request("/api/rooms", { method: "POST", body: "{}" }));
        showMessage("Đã tạo phòng. Hãy gửi mã phòng cho đối thủ.");
    } catch (error) {
        showMessage(error.message, true);
    }
});

document.querySelector("#join-room-form").addEventListener("submit", async function (event) {
    event.preventDefault();
    const form = event.currentTarget;
    const roomCode = new FormData(form).get("roomCode").trim().toUpperCase();
    try {
        renderRoom(await request("/api/rooms/join", { method: "POST", body: JSON.stringify({ roomCode }) }));
        form.reset();
        showMessage("Đã vào phòng.");
    } catch (error) {
        showMessage(error.message, true);
    }
});

readyButton.addEventListener("click", async function () {
    if (!currentRoom) return;
    const ownMember = currentRoom.members.find((member) => member.userId === currentPlayer.userId);
    try {
        renderRoom(await request("/api/rooms/ready", { method: "POST", body: JSON.stringify({ ready: !ownMember?.ready }) }));
        showMessage("Đã cập nhật trạng thái sẵn sàng.");
    } catch (error) {
        showMessage(error.message, true);
    }
});

document.querySelector("#leave-room-button").addEventListener("click", async function () {
    try {
        await request("/api/rooms/leave", { method: "POST", body: "{}" });
        renderRoom(null);
        showMessage("Đã rời phòng.");
    } catch (error) {
        showMessage(error.message, true);
    }
});

document.querySelector("#refresh-room-button").addEventListener("click", async function () {
    try {
        await loadRoom();
        showMessage("Đã làm mới trạng thái phòng.");
    } catch (error) {
        showMessage(error.message, true);
    }
});

document.querySelector("#logout-button").addEventListener("click", async function () {
    try {
        await request("/api/auth/logout", { method: "POST", body: "{}" });
        window.location.replace("/");
    } catch (error) {
        showMessage(error.message, true);
    }
});

rematchButton.addEventListener("click", async function () {
    if (!finishedMatch) return;
    try {
        rematchButton.disabled = true;
        const nextMatch = await request("/api/game/rematch", { method: "POST", body: "{}" });
        if (!nextMatch.finished) {
            window.location.replace("/game.html");
            return;
        }
        renderFinishedRoom(currentRoom, nextMatch);
        showMessage("Đã ghi nhận đồng ý chơi lại. Đang chờ đối thủ.");
    } catch (error) {
        rematchButton.disabled = false;
        showMessage(error.message, true);
    }
});

leaveFinishedRoomButton.addEventListener("click", async function () {
    try {
        leaveFinishedRoomButton.disabled = true;
        await request("/api/game/leave-finished", { method: "POST", body: "{}" });
        renderRoom(null);
        showMessage("Đã rời phòng.");
    } catch (error) {
        leaveFinishedRoomButton.disabled = false;
        showMessage(error.message, true);
    }
});

async function startPage() {
    try {
        currentPlayer = await request("/api/auth/me");
        playerNameElement.textContent = `Xin chào ${currentPlayer.displayName} (${currentPlayer.username})`;
        await loadRoom();
        await loadHistory();
        showMessage(currentRoom ? "Bạn đang ở phòng chờ." : "Chọn tạo phòng hoặc nhập mã phòng để tham gia.");
    } catch (error) {
        if (error.message !== "Phiên đăng nhập đã hết hạn.") showMessage(error.message, true);
    }
}

async function loadHistory() {
    refreshHistoryButton.disabled = true;
    try {
        const history = await request("/api/history");
        const wins = history.filter((item) => item.result === "WIN").length;
        const losses = history.filter((item) => item.result === "LOSS").length;
        historySummary.replaceChildren();
        for (const [label, value] of [["Trận", history.length], ["Thắng", wins], ["Thua", losses]]) {
            const stat = document.createElement("div");
            stat.className = "history-stat";
            stat.innerHTML = `<span>${label}</span><strong>${value}</strong>`;
            historySummary.append(stat);
        }
        historyList.replaceChildren();
        if (history.length === 0) {
            const empty = document.createElement("p");
            empty.className = "subtle";
            empty.textContent = "Chưa có trận đấu nào.";
            historyList.append(empty);
            return;
        }
        for (const item of history) {
            const row = document.createElement("article");
            row.className = "history-item";
            const resultClass = item.result === "WIN" ? "win" : item.result === "LOSS" ? "loss" : "draw";
            const resultText = item.result === "WIN" ? "THẮNG" : item.result === "LOSS" ? "THUA" : "HỦY";
            row.innerHTML = `<div><strong>vs ${escapeHtml(item.opponentName)}</strong><time>${escapeHtml(item.endedAt)}</time></div><span class="history-result ${resultClass}">${resultText} · ${item.scoreYour}:${item.scoreOpponent}</span>`;
            historyList.append(row);
        }
    } catch (error) {
        historyList.replaceChildren();
        const empty = document.createElement("p");
        empty.className = "message error";
        empty.textContent = error.message;
        historyList.append(empty);
    } finally {
        refreshHistoryButton.disabled = false;
    }
}

function escapeHtml(value) {
    const element = document.createElement("span");
    element.textContent = String(value ?? "");
    return element.innerHTML;
}

startPage();
refreshHistoryButton.addEventListener("click", loadHistory);
setInterval(function () {
    if (currentPlayer && currentRoom) {
        loadRoom().catch(() => {});
    }
}, 2000);

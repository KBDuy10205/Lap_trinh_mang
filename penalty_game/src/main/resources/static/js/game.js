const messageElement = document.querySelector("#message");
const roleLabel = document.querySelector("#role-label");
const turnLabel = document.querySelector("#turn-label");
const scoreOne = document.querySelector("#score-one");
const scoreTwo = document.querySelector("#score-two");
const playerOneName = document.querySelector("#player-one-name");
const playerTwoName = document.querySelector("#player-two-name");
const deadlineLabel = document.querySelector("#deadline-label");
const choiceArea = document.querySelector("#choice-area");
const resultArea = document.querySelector("#result-area");
const resultLabel = document.querySelector("#result-label");
const pitch = document.querySelector("#pitch");
const goalTopLabel = document.querySelector("#goal-top-label");
const goalBottomLabel = document.querySelector("#goal-bottom-label");
const localFigure = document.querySelector("#local-figure");
const opponentFigure = document.querySelector("#opponent-figure");
const localFigureLabel = document.querySelector("#local-figure-label");
const opponentFigureLabel = document.querySelector("#opponent-figure-label");
const sceneCaption = document.querySelector("#scene-caption");
const resolutionOverlay = document.querySelector("#resolution-overlay");
const resolutionOutcome = document.querySelector("#resolution-outcome");
const resolutionCountdown = document.querySelector("#resolution-countdown");
const leaveRoomButton = document.querySelector("#leave-room-button");
const directionButtons = [...document.querySelectorAll("[data-direction]")];
let csrfToken = "";
let player = null;
let ownSlot = null;
let match = null;
let submittedTurn = null;
let lastPresentedResolution = 0;
let isPresentingResolution = false;

function showMessage(message, isError = false) {
    messageElement.textContent = message;
    messageElement.classList.toggle("error", isError);
}

async function request(url, options = {}) {
    const method = options.method || "GET";
    const headers = { ...(options.headers || {}) };
    if (method !== "GET") {
        if (!csrfToken) {
            const csrf = await fetch("/api/auth/csrf", { credentials: "same-origin" });
            const csrfBody = await csrf.json();
            csrfToken = csrfBody.token;
        }
        headers["X-CSRF-TOKEN"] = csrfToken;
        headers["Content-Type"] = "application/json";
    }
    const response = await fetch(url, { ...options, method, headers, credentials: "same-origin" });
    const body = await response.json().catch(() => null);
    if (response.status === 401) {
        window.location.replace("/");
        throw new Error("Phiên đăng nhập đã hết hạn.");
    }
    if (!response.ok) {
        const error = new Error(body?.message || "Không thể đồng bộ trận đấu.");
        error.code = body?.code;
        throw error;
    }
    return body;
}

function renderMatch(snapshot) {
    if (isPresentingResolution) {
        return;
    }
    if (snapshot.lastResolvedTurnNo && snapshot.lastResolvedTurnNo > lastPresentedResolution) {
        presentResolution(snapshot);
        return;
    }
    renderActiveMatch(snapshot);
}

function renderActiveMatch(snapshot) {
    match = snapshot;
    scoreOne.textContent = snapshot.scoreSlot1;
    scoreTwo.textContent = snapshot.scoreSlot2;
    turnLabel.textContent = `Lượt ${snapshot.turnNo}`;
    if (snapshot.finished) {
        choiceArea.classList.add("hidden");
        resultArea.classList.remove("hidden");
        resultLabel.textContent = snapshot.winnerSlot === ownSlot ? "Bạn chiến thắng!" : "Đối thủ chiến thắng.";
        showMessage("Kết quả đã được server chốt.");
        return;
    }
    const isShooter = snapshot.shooterSlot === ownSlot;
    roleLabel.textContent = isShooter ? "Vai trò: Người sút" : "Vai trò: Thủ môn";
    pitch.classList.toggle("role-shooter", isShooter);
    pitch.classList.toggle("role-keeper", !isShooter);
    localFigure.classList.toggle("striker", isShooter);
    localFigure.classList.toggle("keeper", !isShooter);
    opponentFigure.classList.toggle("keeper", isShooter);
    opponentFigure.classList.toggle("striker", !isShooter);
    localFigureLabel.textContent = isShooter ? "Bạn · Người sút" : "Bạn · Thủ môn";
    opponentFigureLabel.textContent = isShooter ? "Thủ môn đối phương" : "Đối thủ · Người sút";
    goalTopLabel.textContent = isShooter ? "Khung thành đối phương" : "Đối thủ sút từ phía này";
    goalBottomLabel.textContent = isShooter ? "Khu vực của bạn" : "Khung thành của bạn";
    sceneCaption.textContent = isShooter ? "Tấn công khung thành đối phương" : "Bảo vệ khung thành của bạn";
    const secondsLeft = Math.max(0, Math.ceil((snapshot.deadline - Date.now()) / 1000));
    deadlineLabel.textContent = `Còn ${secondsLeft} giây để chọn.`;
    const alreadySubmitted = submittedTurn === snapshot.turnNo;
    directionButtons.forEach((button) => { button.disabled = alreadySubmitted; });
    document.querySelector("#choice-hint").textContent = alreadySubmitted
        ? "Đã khóa lựa chọn của bạn. Đang chờ đối thủ hoặc hết giờ."
        : "Chọn kín một hướng trước khi hết giờ.";
    showMessage("Đang chơi.");
}

function presentResolution(snapshot) {
    isPresentingResolution = true;
    lastPresentedResolution = snapshot.lastResolvedTurnNo;
    match = snapshot;
    submittedTurn = null;
    const outcomeLabels = { GOAL: "GOAL!", SAVED: "CẢN PHÁ!", MISS: "SÚT TRƯỢT!" };
    resolutionOutcome.textContent = outcomeLabels[snapshot.lastOutcome] || "KẾT QUẢ LƯỢT ĐẤU";
    resolutionOverlay.classList.remove("hidden");
    pitch.classList.add("resolving");
    directionButtons.forEach((button) => { button.disabled = true; });
    showMessage("Server đã chốt kết quả lượt đấu. Chuẩn bị đổi vai...");

    let seconds = 3;
    resolutionCountdown.textContent = seconds;
    const interval = window.setInterval(function () {
        seconds -= 1;
        resolutionCountdown.textContent = Math.max(seconds, 0);
    }, 1000);
    window.setTimeout(function () {
        window.clearInterval(interval);
        resolutionOverlay.classList.add("hidden");
        pitch.classList.remove("resolving");
        isPresentingResolution = false;
        renderActiveMatch(snapshot);
    }, 3000);
}

async function loadInitialState() {
    player = await request("/api/auth/me");
    const room = await request("/api/rooms/current");
    if (!room || room.status !== "PLAYING") {
        window.location.replace("/home.html");
        return;
    }
    const ownMember = room.members.find((member) => member.userId === player.userId);
    ownSlot = ownMember?.slot;
    playerOneName.textContent = room.members.find((member) => member.slot === 1)?.displayName || "Người chơi 1";
    playerTwoName.textContent = room.members.find((member) => member.slot === 2)?.displayName || "Người chơi 2";
    await refreshMatch();
}

async function refreshMatch() {
    try {
        renderMatch(await request("/api/game/current"));
    } catch (error) {
        if (error.code === "MATCH_UNAVAILABLE") {
            window.location.replace("/home.html");
            return;
        }
        if (error.message !== "Phiên đăng nhập đã hết hạn.") showMessage(error.message, true);
    }
}

directionButtons.forEach((button) => {
    button.addEventListener("click", async function () {
        if (!match || isPresentingResolution || submittedTurn === match.turnNo) return;
        try {
            submittedTurn = match.turnNo;
            renderMatch(match);
            await request("/api/game/choice", { method: "POST", body: JSON.stringify({ direction: button.dataset.direction }) });
            await refreshMatch();
        } catch (error) {
            submittedTurn = null;
            showMessage(error.message, true);
        }
    });
});

leaveRoomButton.addEventListener("click", async function () {
    if (!window.confirm("Rời phòng sẽ tính là bỏ cuộc. Bạn có muốn tiếp tục?")) return;
    try {
        leaveRoomButton.disabled = true;
        await request("/api/game/forfeit", { method: "POST", body: "{}" });
        window.location.replace("/home.html");
    } catch (error) {
        leaveRoomButton.disabled = false;
        showMessage(error.message, true);
    }
});

loadInitialState().catch((error) => showMessage(error.message, true));
setInterval(function () { if (player && !match?.finished) refreshMatch(); }, 1000);

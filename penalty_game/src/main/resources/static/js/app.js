const messageElement = document.querySelector("#message");
const guestArea = document.querySelector("#guest-area");
const playerArea = document.querySelector("#player-area");
const playerName = document.querySelector("#player-name");
let csrfToken = "";

function showMessage(message, isError) {
    messageElement.textContent = message;
    messageElement.classList.toggle("error", isError);
}

function showPlayer(player) {
    playerName.textContent = `Xin chào ${player.displayName} (${player.username}).`;
    guestArea.classList.add("hidden");
    playerArea.classList.remove("hidden");
}

function showGuest() {
    guestArea.classList.remove("hidden");
    playerArea.classList.add("hidden");
}

async function sendJson(url, data) {
    const response = await fetch(url, {
        method: "POST",
        credentials: "same-origin",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrfToken
        },
        body: JSON.stringify(data)
    });

    if (response.status === 204) {
        return null;
    }

    const contentType = response.headers.get("content-type") || "";
    const responseData = contentType.includes("application/json") ? await response.json() : {};
    if (!response.ok) {
        throw new Error(responseData.message || "Yêu cầu không thành công.");
    }

    return responseData;
}

document.querySelector("#register-form").addEventListener("submit", async function (event) {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    try {
        const player = await sendJson("/api/auth/register", {
            username: formData.get("username"),
            displayName: formData.get("displayName"),
            password: formData.get("password")
        });
        await loadCsrfToken();
        showPlayer(player);
        showMessage("Tạo tài khoản và đăng nhập thành công.", false);
        event.currentTarget.reset();
    } catch (error) {
        showMessage(error.message, true);
    }
});

document.querySelector("#login-form").addEventListener("submit", async function (event) {
    event.preventDefault();
    const formData = new FormData(event.currentTarget);

    try {
        const player = await sendJson("/api/auth/login", {
            username: formData.get("username"),
            password: formData.get("password")
        });
        await loadCsrfToken();
        showPlayer(player);
        showMessage("Đăng nhập thành công.", false);
        event.currentTarget.reset();
    } catch (error) {
        showMessage(error.message, true);
    }
});

document.querySelector("#logout-button").addEventListener("click", async function () {
    try {
        await sendJson("/api/auth/logout", {});
        showGuest();
        showMessage("Đã đăng xuất.", false);
    } catch (error) {
        showMessage(error.message, true);
    }
});

async function loadCurrentPlayer() {
    const response = await fetch("/api/auth/me", { credentials: "same-origin" });
    if (response.status === 401) {
        return;
    }

    if (!response.ok) {
        showMessage("Không thể kiểm tra phiên đăng nhập.", true);
        return;
    }

    const player = await response.json();
    showPlayer(player);
}

async function loadCsrfToken() {
    const response = await fetch("/api/auth/csrf", { credentials: "same-origin" });
    const responseData = await response.json();
    csrfToken = responseData.token;
}

async function startPage() {
    await loadCsrfToken();
    await loadCurrentPlayer();
}

startPage();

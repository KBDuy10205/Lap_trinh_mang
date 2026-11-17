const messageElement = document.querySelector("#message");
const loginButton = document.querySelector("#login-button");
let csrfToken = "";
let csrfTokenRequest = null;

function showMessage(message, isError) {
    messageElement.textContent = message;
    messageElement.classList.toggle("error", isError);
}

async function sendJson(url, data) {
    if (!csrfToken) {
        await loadCsrfToken();
    }

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
        if (response.status === 403) {
            throw new Error("Phiên bảo mật chưa sẵn sàng hoặc đã hết hạn. Hãy tải lại trang và thử lại.");
        }
        throw new Error(responseData.message || "Yêu cầu không thành công.");
    }

    return responseData;
}

document.querySelector("#login-form").addEventListener("submit", async function (event) {
    event.preventDefault();
    const loginForm = event.currentTarget;
    const formData = new FormData(loginForm);

    try {
        loginButton.disabled = true;
        showMessage("Đang đăng nhập...", false);
        const player = await sendJson("/api/auth/login", {
            username: formData.get("username"),
            password: formData.get("password")
        });
        await loadCsrfToken(true);
        loginForm.reset();
        window.location.assign("/home.html");
    } catch (error) {
        showMessage(error.message, true);
    } finally {
        loginButton.disabled = false;
    }
});

async function loadCurrentPlayer() {
    const response = await fetch("/api/auth/me", { credentials: "same-origin" });
    if (response.status === 401) {
        return false;
    }

    if (!response.ok) {
        showMessage("Không thể kiểm tra phiên đăng nhập.", true);
        return null;
    }

    await response.json();
    return true;
}

async function loadCsrfToken(forceRefresh = false) {
    if (!forceRefresh && csrfToken) {
        return csrfToken;
    }
    if (csrfTokenRequest) {
        return csrfTokenRequest;
    }

    csrfTokenRequest = fetch("/api/auth/csrf", { credentials: "same-origin" })
        .then(async function (response) {
            if (!response.ok) {
                throw new Error("Không thể tạo phiên bảo mật.");
            }
            const responseData = await response.json();
            if (!responseData.token) {
                throw new Error("Không nhận được token bảo mật.");
            }
            csrfToken = responseData.token;
            return csrfToken;
        })
        .finally(function () {
            csrfTokenRequest = null;
        });

    return csrfTokenRequest;
}

async function startPage() {
    try {
        await loadCsrfToken();
    } catch (error) {
        // Lần gửi biểu mẫu sẽ thử lấy lại token và hiển thị lỗi nếu vẫn không được.
    }
    const hasCurrentSession = await loadCurrentPlayer();
    if (hasCurrentSession === true) {
        window.location.replace("/home.html");
    } else if (hasCurrentSession === false) {
        showMessage("Đăng nhập để bắt đầu.", false);
    }
}

startPage();

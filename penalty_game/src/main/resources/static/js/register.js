const messageElement = document.querySelector("#message");
const registerForm = document.querySelector("#register-form");
const registerButton = document.querySelector("#register-button");
let csrfToken = "";

function showMessage(message, isError) {
    messageElement.textContent = message;
    messageElement.classList.toggle("error", isError);
}

async function loadCsrfToken() {
    const response = await fetch("/api/auth/csrf", { credentials: "same-origin" });
    if (!response.ok) {
        throw new Error("Không thể tạo phiên bảo mật.");
    }
    const responseData = await response.json();
    if (!responseData.token) {
        throw new Error("Không nhận được token bảo mật.");
    }
    csrfToken = responseData.token;
}

async function register(data) {
    const response = await fetch("/api/auth/register", {
        method: "POST",
        credentials: "same-origin",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrfToken
        },
        body: JSON.stringify(data)
    });

    const contentType = response.headers.get("content-type") || "";
    const responseData = contentType.includes("application/json") ? await response.json() : {};
    if (!response.ok) {
        if (response.status === 403) {
            throw new Error("Phiên bảo mật chưa sẵn sàng hoặc đã hết hạn. Hãy tải lại trang.");
        }
        throw new Error(responseData.message || "Không thể tạo tài khoản.");
    }
}

registerForm.addEventListener("submit", async function (event) {
    event.preventDefault();
    const formData = new FormData(registerForm);
    const password = formData.get("password");
    const confirmPassword = formData.get("confirmPassword");

    if (password !== confirmPassword) {
        showMessage("Xác nhận mật khẩu không khớp.", true);
        return;
    }

    try {
        registerButton.disabled = true;
        showMessage("Đang tạo tài khoản...", false);
        if (!csrfToken) {
            await loadCsrfToken();
        }
        await register({
            email: formData.get("email"),
            username: formData.get("username"),
            password: password,
            confirmPassword: confirmPassword
        });
        window.location.assign("/");
    } catch (error) {
        showMessage(error.message, true);
    } finally {
        registerButton.disabled = false;
    }
});

async function prepareCsrfToken() {
    try {
        await loadCsrfToken();
    } catch (error) {
        // Lần gửi biểu mẫu sẽ thử lấy lại token và hiển thị lỗi nếu vẫn không được.
    }
}

prepareCsrfToken();

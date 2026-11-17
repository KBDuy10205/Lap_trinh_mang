package penaltygame.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import penaltygame.account.AuthController;

/** Lấy mã người chơi đã xác thực từ HttpSession, không nhận từ payload. */
public final class SessionUser {

	private SessionUser() {
	}

	public static Long getUserId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || !(session.getAttribute(AuthController.SESSION_USER_ID) instanceof Long userId)) {
			return null;
		}
		return userId;
	}
}

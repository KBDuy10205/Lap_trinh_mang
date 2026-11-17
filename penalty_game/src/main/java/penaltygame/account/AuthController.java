package penaltygame.account;

import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import penaltygame.config.CsrfTokenFilter;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	public static final String SESSION_USER_ID = "authenticatedUserId";

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
		try {
			AuthService.Player player = authService.register(registerRequest.email(), registerRequest.username(),
					registerRequest.password(), registerRequest.confirmPassword());
			createLoginSession(request, player.userId());
			return ResponseEntity.status(HttpStatus.CREATED).body(PlayerResponse.from(player));
		} catch (AuthService.AuthException exception) {
			if ("USERNAME_TAKEN".equals(exception.getCode()) || "EMAIL_TAKEN".equals(exception.getCode())
					|| "ACCOUNT_TAKEN".equals(exception.getCode())) {
				return errorResponse(exception, HttpStatus.CONFLICT);
			}

			return errorResponse(exception, HttpStatus.BAD_REQUEST);
		} catch (DataAccessException exception) {
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(Map.of("code", "DATABASE_UNAVAILABLE",
							"message", "Chưa kết nối được MySQL; chưa thể tạo tài khoản."));
		}
	}

	@GetMapping("/csrf")
	public Map<String, String> getCsrfToken(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		String token = (String) session.getAttribute(CsrfTokenFilter.SESSION_TOKEN_NAME);

		if (token == null) {
			token = UUID.randomUUID().toString();
			session.setAttribute(CsrfTokenFilter.SESSION_TOKEN_NAME, token);
		}

		return Map.of("token", token);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
		try {
			AuthService.Player player = authService.login(loginRequest.username(), loginRequest.password());
			createLoginSession(request, player.userId());
			return ResponseEntity.ok(PlayerResponse.from(player));
		} catch (AuthService.AuthException exception) {
			if ("INVALID_CREDENTIALS".equals(exception.getCode())) {
				return errorResponse(exception, HttpStatus.UNAUTHORIZED);
			}

			return errorResponse(exception, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentPlayer(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || !(session.getAttribute(SESSION_USER_ID) instanceof Long userId)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("code", "UNAUTHENTICATED", "message", "Bạn chưa đăng nhập."));
		}

		AuthService.Player player = authService.getPlayer(userId);
		if (player == null) {
			session.invalidate();
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("code", "UNAUTHENTICATED", "message", "Phiên đăng nhập không còn hợp lệ."));
		}

		return ResponseEntity.ok(PlayerResponse.from(player));
	}

	private void createLoginSession(HttpServletRequest request, long userId) {
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate();
		}

		HttpSession newSession = request.getSession(true);
		newSession.setAttribute(SESSION_USER_ID, userId);
		newSession.setAttribute(CsrfTokenFilter.SESSION_TOKEN_NAME, UUID.randomUUID().toString());
	}

	private ResponseEntity<Map<String, String>> errorResponse(AuthService.AuthException exception, HttpStatus status) {
		return ResponseEntity.status(status)
				.body(Map.of("code", exception.getCode(), "message", exception.getMessage()));
	}

	public record RegisterRequest(String email, String username, String password, String confirmPassword) {
	}

	public record LoginRequest(String username, String password) {
	}

	public record PlayerResponse(long userId, String username, String displayName) {

		public static PlayerResponse from(AuthService.Player player) {
			return new PlayerResponse(player.userId(), player.username(), player.displayName());
		}
	}
}

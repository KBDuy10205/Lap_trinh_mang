package penaltygame.account;

import java.util.Locale;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Player register(String email, String username, String password, String confirmPassword) {
		String normalizedEmail = validateEmail(email);
		String normalizedUsername = validateUsername(username);
		String validatedPassword = validatePassword(password);
		validatePasswordConfirmation(validatedPassword, confirmPassword);

		if (accountRepository.findByUsername(normalizedUsername) != null) {
			throw new AuthException("USERNAME_TAKEN", "Tên đăng nhập đã được sử dụng.");
		}
		if (accountRepository.existsByEmail(normalizedEmail)) {
			throw new AuthException("EMAIL_TAKEN", "Email đã được sử dụng.");
		}

		try {
			String passwordHash = passwordEncoder.encode(validatedPassword);
			long userId = accountRepository.createAccount(normalizedUsername, normalizedEmail, passwordHash);
			accountRepository.createProfile(userId, normalizedUsername);
			return new Player(userId, normalizedUsername, normalizedUsername);
		} catch (DuplicateKeyException exception) {
			// UNIQUE ở MySQL vẫn bảo vệ khi hai yêu cầu cùng đăng ký username/email.
			throw new AuthException("ACCOUNT_TAKEN", "Tên đăng nhập hoặc email đã được sử dụng.");
		}
	}

	public Player login(String username, String password) {
		String normalizedUsername = validateUsername(username);
		String validatedPassword = validatePassword(password);
		AccountRepository.Account account = accountRepository.findByUsername(normalizedUsername);

		if (account == null || !"ACTIVE".equals(account.status())
				|| !passwordEncoder.matches(validatedPassword, account.passwordHash())) {
			throw new AuthException("INVALID_CREDENTIALS", "Tên đăng nhập hoặc mật khẩu không đúng.");
		}

		return new Player(account.userId(), account.username(), account.displayName());
	}

	public Player getPlayer(long userId) {
		AccountRepository.Account account = accountRepository.findByUserId(userId);

		if (account == null || !"ACTIVE".equals(account.status())) {
			return null;
		}

		return new Player(account.userId(), account.username(), account.displayName());
	}

	private String validateUsername(String username) {
		if (username == null) {
			throw new AuthException("VALIDATION_ERROR", "Tên đăng nhập là bắt buộc.");
		}

		String normalizedUsername = username.trim().toLowerCase(Locale.ROOT);
		if (!normalizedUsername.matches("[a-z0-9_]{3,50}")) {
			throw new AuthException("VALIDATION_ERROR",
					"Tên đăng nhập gồm 3-50 ký tự chữ thường, số hoặc dấu gạch dưới.");
		}

		return normalizedUsername;
	}

	private String validateEmail(String email) {
		if (email == null) {
			throw new AuthException("VALIDATION_ERROR", "Email là bắt buộc.");
		}

		String normalizedEmail = email.trim().toLowerCase(Locale.ROOT);
		if (normalizedEmail.length() > 254
				|| !normalizedEmail.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
			throw new AuthException("VALIDATION_ERROR", "Email không đúng định dạng.");
		}

		return normalizedEmail;
	}

	private String validatePassword(String password) {
		if (password == null || password.length() < 8 || password.length() > 72) {
			throw new AuthException("VALIDATION_ERROR", "Mật khẩu phải dài từ 8 đến 72 ký tự.");
		}

		return password;
	}

	private void validatePasswordConfirmation(String password, String confirmPassword) {
		if (confirmPassword == null || !password.equals(confirmPassword)) {
			throw new AuthException("PASSWORD_CONFIRMATION_MISMATCH", "Xác nhận mật khẩu không khớp.");
		}
	}

	public record Player(long userId, String username, String displayName) {
	}

	public static class AuthException extends RuntimeException {

		private final String code;

		public AuthException(String code, String message) {
			super(message);
			this.code = code;
		}

		public String getCode() {
			return code;
		}
	}
}

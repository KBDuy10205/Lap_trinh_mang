package penaltygame.config;

import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Điểm kiểm tra thủ công cho cấu hình MySQL ở Bước 1.
 * Chỉ chạy SELECT 1, không đọc hoặc thay đổi dữ liệu game.
 */
@RestController
@RequestMapping("/api/health")
public class DatabaseHealthController {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseHealthController.class);

	private final JdbcTemplate jdbcTemplate;

	public DatabaseHealthController(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@GetMapping("/database")
	public ResponseEntity<Map<String, String>> checkDatabaseConnection() {
		try {
			Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);

			if (result != null && result == 1) {
				return ResponseEntity.ok(Map.of("status", "UP"));
			}
		} catch (DataAccessException exception) {
			Throwable cause = exception.getMostSpecificCause();
			String reason = diagnosticReason(cause == null ? exception.getMessage() : cause.getMessage());
			logger.warn("Database health check failed ({})", reason);
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(Map.of("status", "DOWN", "reason", reason));
			// Không trả chi tiết lỗi kết nối để tránh lộ cấu hình cơ sở dữ liệu.
		}

		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
				.body(Map.of("status", "DOWN", "message", "Không kết nối được MySQL."));
	}

	private String diagnosticReason(String databaseMessage) {
		String message = databaseMessage == null ? "" : databaseMessage.toLowerCase();
		if (message.contains("access denied")) {
			return "AUTHENTICATION_FAILED";
		}
		if (message.contains("unknown database")) {
			return "DATABASE_NOT_FOUND";
		}
		if (message.contains("communications link failure") || message.contains("connection refused")
				|| message.contains("connect timed out")) {
			return "CONNECTION_FAILED";
		}
		return "DATABASE_QUERY_FAILED";
	}
}

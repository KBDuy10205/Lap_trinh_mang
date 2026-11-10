package penaltygame.account;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepository {

	private final JdbcTemplate jdbcTemplate;

	public AccountRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Account findByUsername(String username) {
		String sql = "SELECT a.user_id, a.username, a.password_hash, a.status, p.display_name "
				+ "FROM accounts a JOIN player_profiles p ON p.user_id = a.user_id "
				+ "WHERE a.username = ?";

		List<Account> accounts = jdbcTemplate.query(sql, (resultSet, rowNumber) -> new Account(
				resultSet.getLong("user_id"),
				resultSet.getString("username"),
				resultSet.getString("password_hash"),
				resultSet.getString("status"),
				resultSet.getString("display_name")), username);

		if (accounts.isEmpty()) {
			return null;
		}

		return accounts.get(0);
	}

	public Account findByUserId(long userId) {
		String sql = "SELECT a.user_id, a.username, a.password_hash, a.status, p.display_name "
				+ "FROM accounts a JOIN player_profiles p ON p.user_id = a.user_id "
				+ "WHERE a.user_id = ?";

		List<Account> accounts = jdbcTemplate.query(sql, (resultSet, rowNumber) -> new Account(
				resultSet.getLong("user_id"),
				resultSet.getString("username"),
				resultSet.getString("password_hash"),
				resultSet.getString("status"),
				resultSet.getString("display_name")), userId);

		if (accounts.isEmpty()) {
			return null;
		}

		return accounts.get(0);
	}

	public long createAccount(String username, String passwordHash) {
		String sql = "INSERT INTO accounts (username, password_hash) VALUES (?, ?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, username);
			statement.setString(2, passwordHash);
			return statement;
		}, keyHolder);

		Number userId = keyHolder.getKey();
		if (userId == null) {
			throw new IllegalStateException("Không lấy được mã tài khoản vừa tạo.");
		}

		return userId.longValue();
	}

	public void createProfile(long userId, String displayName) {
		String sql = "INSERT INTO player_profiles (user_id, display_name) VALUES (?, ?)";
		jdbcTemplate.update(sql, userId, displayName);
	}

	public record Account(long userId, String username, String passwordHash, String status, String displayName) {
	}
}

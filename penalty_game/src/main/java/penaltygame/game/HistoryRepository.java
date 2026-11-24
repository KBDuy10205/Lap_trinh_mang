package penaltygame.game;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryRepository {

	private final JdbcTemplate jdbcTemplate;

	public HistoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long startMatch(long roomId, int firstShooterSlot, long userSlot1, long userSlot2) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO matches (room_id, status, first_shooter_slot) VALUES (?, 'PLAYING', ?)",
					Statement.RETURN_GENERATED_KEYS);
			statement.setLong(1, roomId);
			statement.setInt(2, firstShooterSlot);
			return statement;
		}, keyHolder);
		long matchId = keyHolder.getKey().longValue();
		jdbcTemplate.update("INSERT INTO match_players (match_id, slot, user_id) VALUES (?, 1, ?)",
				matchId, userSlot1);
		jdbcTemplate.update("INSERT INTO match_players (match_id, slot, user_id) VALUES (?, 2, ?)",
				matchId, userSlot2);
		return matchId;
	}

	public void recordResult(long matchId, Integer winnerSlot, String endReason, int scoreSlot1, int scoreSlot2) {
		jdbcTemplate.update(
				"INSERT INTO match_results "
				+ "(match_id, winner_slot, score_slot1, score_slot2, end_reason) "
				+ "VALUES (?, ?, ?, ?, ?) "
				+ "ON DUPLICATE KEY UPDATE winner_slot = VALUES(winner_slot), "
				+ "score_slot1 = VALUES(score_slot1), score_slot2 = VALUES(score_slot2), "
				+ "end_reason = VALUES(end_reason)",
				matchId, winnerSlot, scoreSlot1, scoreSlot2, endReason);
		jdbcTemplate.update("UPDATE matches SET status = 'FINISHED' WHERE match_id = ?", matchId);
	}

	public List<HistoryEntry> findHistory(long userId) {
		String sql = """
				SELECT mp.match_id, mp.slot, mr.winner_slot, mr.score_slot1, mr.score_slot2,
				       mr.end_reason, mr.ended_at,
				       opponent.user_id AS opponent_id, pp.display_name AS opponent_name
				FROM match_players mp
				JOIN match_results mr ON mr.match_id = mp.match_id
				JOIN match_players opponent ON opponent.match_id = mp.match_id AND opponent.slot <> mp.slot
				JOIN player_profiles pp ON pp.user_id = opponent.user_id
				WHERE mp.user_id = ?
				ORDER BY mr.ended_at DESC, mp.match_id DESC
				LIMIT 50
				""";
		return jdbcTemplate.query(sql, (rs, rowNumber) -> {
			int ownSlot = rs.getInt("slot");
			Integer winnerSlot = (Integer) rs.getObject("winner_slot");
			String endReason = rs.getString("end_reason");
			String result = "CANCELLED".equals(endReason) || winnerSlot == null
					? "DRAW"
					: winnerSlot == ownSlot ? "WIN" : "LOSS";
			int scoreYour = ownSlot == 1 ? rs.getInt("score_slot1") : rs.getInt("score_slot2");
			int scoreOpponent = ownSlot == 1 ? rs.getInt("score_slot2") : rs.getInt("score_slot1");
			Timestamp endedAt = rs.getTimestamp("ended_at");
			String endedAtText = endedAt == null ? "" : DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
					.format(endedAt.toLocalDateTime());
			return new HistoryEntry(rs.getLong("match_id"), rs.getString("opponent_name"),
					result, scoreYour, scoreOpponent, endedAtText);
		}, userId);
	}

	public record HistoryEntry(long matchId, String opponentName, String result,
			int scoreYour, int scoreOpponent, String endedAt) {
	}
}

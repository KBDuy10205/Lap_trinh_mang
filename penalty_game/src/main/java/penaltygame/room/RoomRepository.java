package penaltygame.room;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RoomRepository {

	private final JdbcTemplate jdbcTemplate;

	public RoomRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Room findRoomForUpdate(String roomCode) {
		String sql = "SELECT room_id, room_code, owner_id, status FROM rooms WHERE room_code = ? FOR UPDATE";
		List<Room> rooms = jdbcTemplate.query(sql, (rs, row) -> new Room(rs.getLong("room_id"),
				rs.getString("room_code"), rs.getLong("owner_id"), rs.getString("status")), roomCode);
		return rooms.isEmpty() ? null : rooms.get(0);
	}

	public Room findRoomForMember(long userId) {
		String sql = "SELECT r.room_id, r.room_code, r.owner_id, r.status FROM rooms r "
				+ "JOIN room_members rm ON rm.room_id = r.room_id WHERE rm.user_id = ?";
		List<Room> rooms = jdbcTemplate.query(sql, (rs, row) -> new Room(rs.getLong("room_id"),
				rs.getString("room_code"), rs.getLong("owner_id"), rs.getString("status")), userId);
		return rooms.isEmpty() ? null : rooms.get(0);
	}

	public Room findRoomForUpdateById(long roomId) {
		String sql = "SELECT room_id, room_code, owner_id, status FROM rooms WHERE room_id = ? FOR UPDATE";
		List<Room> rooms = jdbcTemplate.query(sql, (rs, row) -> new Room(rs.getLong("room_id"),
				rs.getString("room_code"), rs.getLong("owner_id"), rs.getString("status")), roomId);
		return rooms.isEmpty() ? null : rooms.get(0);
	}

	public long createRoom(String roomCode, long ownerId) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO rooms (room_code, owner_id, status) VALUES (?, ?, 'WAITING')",
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, roomCode);
			statement.setLong(2, ownerId);
			return statement;
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	public void addMember(long roomId, int slot, long userId) {
		jdbcTemplate.update("INSERT INTO room_members (room_id, slot, user_id, is_ready) VALUES (?, ?, ?, FALSE)",
				roomId, slot, userId);
	}

	public List<Member> findMembers(long roomId) {
		String sql = "SELECT rm.slot, rm.user_id, rm.is_ready, pp.display_name FROM room_members rm "
				+ "JOIN player_profiles pp ON pp.user_id = rm.user_id WHERE rm.room_id = ? ORDER BY rm.slot";
		return jdbcTemplate.query(sql, (rs, row) -> new Member(rs.getInt("slot"), rs.getLong("user_id"),
				rs.getBoolean("is_ready"), rs.getString("display_name")), roomId);
	}

	public void setReady(long roomId, long userId, boolean ready) {
		jdbcTemplate.update("UPDATE room_members SET is_ready = ? WHERE room_id = ? AND user_id = ?", ready, roomId, userId);
	}

	public void deleteMember(long roomId, long userId) {
		jdbcTemplate.update("DELETE FROM room_members WHERE room_id = ? AND user_id = ?", roomId, userId);
	}

	public void changeOwner(long roomId, long ownerId) {
		jdbcTemplate.update("UPDATE rooms SET owner_id = ? WHERE room_id = ?", ownerId, roomId);
	}

	public void setRoomStatus(long roomId, String status) {
		jdbcTemplate.update("UPDATE rooms SET status = ? WHERE room_id = ?", status, roomId);
	}

	public record Room(long roomId, String roomCode, long ownerId, String status) {
	}

	public record Member(int slot, long userId, boolean ready, String displayName) {
	}
}

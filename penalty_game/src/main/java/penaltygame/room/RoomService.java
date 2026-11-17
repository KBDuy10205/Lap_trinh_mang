package penaltygame.room;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

	private static final char[] CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray();
	private final RoomRepository roomRepository;
	private final SecureRandom random = new SecureRandom();

	public RoomService(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	@Transactional
	public Snapshot create(long userId) {
		ensureNotInRoom(userId);
		for (int attempt = 0; attempt < 5; attempt++) {
			String code = createCode();
			try {
				long roomId = roomRepository.createRoom(code, userId);
				roomRepository.addMember(roomId, 1, userId);
				return getSnapshot(roomId);
			} catch (DuplicateKeyException exception) {
				// Mã ngẫu nhiên trùng: thử lại, không trả mã đã lỗi.
			}
		}
		throw new RoomException("ROOM_CODE_ERROR", "Không tạo được mã phòng, hãy thử lại.");
	}

	@Transactional
	public Snapshot join(long userId, String requestedCode) {
		ensureNotInRoom(userId);
		String code = normalizeCode(requestedCode);
		RoomRepository.Room room = roomRepository.findRoomForUpdate(code);
		if (room == null) throw new RoomException("ROOM_NOT_FOUND", "Không tìm thấy phòng.");
		if (!"WAITING".equals(room.status())) throw new RoomException("ROOM_NOT_JOINABLE", "Phòng không còn chờ người chơi.");
		List<RoomRepository.Member> members = roomRepository.findMembers(room.roomId());
		if (members.size() >= 2) throw new RoomException("ROOM_FULL", "Phòng đã đủ hai người.");
		int slot = members.isEmpty() || members.get(0).slot() == 2 ? 1 : 2;
		roomRepository.addMember(room.roomId(), slot, userId);
		return getSnapshot(room.roomId());
	}

	@Transactional
	public Snapshot setReady(long userId, boolean ready) {
		RoomRepository.Room room = requireRoom(userId);
		if (!"WAITING".equals(room.status())) throw new RoomException("ROOM_NOT_WAITING", "Trận đang diễn ra.");
		roomRepository.setReady(room.roomId(), userId, ready);
		return getSnapshot(room.roomId());
	}

	@Transactional
	public Snapshot leave(long userId) {
		RoomRepository.Room room = requireRoom(userId);
		if (!"WAITING".equals(room.status())) throw new RoomException("MATCH_IN_PROGRESS", "Hãy bỏ cuộc khi trận đang diễn ra.");
		roomRepository.deleteMember(room.roomId(), userId);
		List<RoomRepository.Member> remaining = roomRepository.findMembers(room.roomId());
		if (remaining.isEmpty()) roomRepository.setRoomStatus(room.roomId(), "CLOSED");
		else if (room.ownerId() == userId) roomRepository.changeOwner(room.roomId(), remaining.get(0).userId());
		return getSnapshot(room.roomId());
	}

	@Transactional
	public void leaveAfterForfeit(long userId) {
		RoomRepository.Room room = requireRoom(userId);
		roomRepository.deleteMember(room.roomId(), userId);
		List<RoomRepository.Member> remaining = roomRepository.findMembers(room.roomId());
		if (remaining.isEmpty()) {
			roomRepository.setRoomStatus(room.roomId(), "CLOSED");
			return;
		}
		if (room.ownerId() == userId) roomRepository.changeOwner(room.roomId(), remaining.get(0).userId());
		roomRepository.setRoomStatus(room.roomId(), "WAITING");
	}

	public Snapshot current(long userId) {
		RoomRepository.Room room = roomRepository.findRoomForMember(userId);
		return room == null ? null : getSnapshot(room.roomId());
	}

	public RoomRepository.Room requireRoom(long userId) {
		RoomRepository.Room room = roomRepository.findRoomForMember(userId);
		if (room == null) throw new RoomException("NOT_IN_ROOM", "Bạn chưa ở trong phòng.");
		return room;
	}

	@Transactional
	public void markInProgress(long roomId) {
		RoomRepository.Room room = roomRepository.findRoomForUpdateById(roomId);
		if (room == null) {
			throw new RoomException("ROOM_NOT_FOUND", "Không tìm thấy phòng.");
		}
		roomRepository.setRoomStatus(roomId, "PLAYING");
	}

	@Transactional
	public void markWaiting(long roomId) {
		RoomRepository.Room room = roomRepository.findRoomForUpdateById(roomId);
		if (room != null) {
			roomRepository.setRoomStatus(roomId, "WAITING");
		}
	}

	public Snapshot getSnapshot(long roomId) {
		RoomRepository.Room room = roomRepository.findRoomForUpdateById(roomId);
		List<RoomRepository.Member> members = roomRepository.findMembers(roomId);
		return new Snapshot(room.roomId(), room.roomCode(), room.ownerId(), room.status(), members);
	}

	private void ensureNotInRoom(long userId) {
		if (roomRepository.findRoomForMember(userId) != null) throw new RoomException("ALREADY_IN_ROOM", "Bạn đang ở một phòng khác.");
	}

	private String normalizeCode(String code) {
		if (code == null || !code.trim().matches("[A-Za-z0-9]{8}")) throw new RoomException("INVALID_ROOM_CODE", "Mã phòng gồm 8 ký tự.");
		return code.trim().toUpperCase();
	}

	private String createCode() {
		StringBuilder code = new StringBuilder(8);
		for (int index = 0; index < 8; index++) code.append(CODE_CHARS[random.nextInt(CODE_CHARS.length)]);
		return code.toString();
	}

	public record Snapshot(long roomId, String roomCode, long ownerId, String status, List<RoomRepository.Member> members) {}
	public static class RoomException extends RuntimeException { public final String code; public RoomException(String code, String message) { super(message); this.code = code; } }
}

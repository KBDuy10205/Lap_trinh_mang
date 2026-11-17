package penaltygame.game;

import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import penaltygame.room.RoomService;

/** Trạng thái realtime tối giản trong một tiến trình; MySQL là nơi sẽ lưu lịch sử sau khi DB sẵn sàng. */
@Service
public class GameSessionService {

	private final Map<Long, MatchSession> matches = new ConcurrentHashMap<>();
	private final RoomService roomService;

	public GameSessionService(RoomService roomService) { this.roomService = roomService; }

	public synchronized Snapshot start(long userId) {
		RoomService.Snapshot room = roomService.current(userId);
		if (room == null || room.ownerId() != userId) throw new GameException("NOT_OWNER", "Chỉ chủ phòng được bắt đầu.");
		if (room.members().size() != 2) throw new GameException("PLAYERS_NOT_READY", "Cần đủ hai người chơi để bắt đầu.");
		MatchSession existing = matches.get(room.roomId());
		if (existing != null) return snapshot(room.roomId(), existing);
		MatchSession match = new MatchSession(room.members().get(0).userId(), room.members().get(1).userId(), new GameEngine(1), System.currentTimeMillis() + 15_000L);
		matches.put(room.roomId(), match);
		roomService.markInProgress(room.roomId());
		return snapshot(room.roomId(), match);
	}

	public synchronized Snapshot submit(long userId, Direction direction) {
		long roomId = requireRoomId(userId);
		MatchSession match = requireMatch(roomId);
		if (System.currentTimeMillis() > match.deadline) resolveTurn(match);
		int slot = match.slotOf(userId);
		if (slot == 0) throw new GameException("NOT_IN_MATCH", "Bạn không thuộc trận này.");
		GameEngine.TurnResult result = match.engine.submitChoice(slot, direction);
		if (result != null) {
			match.lastResult = result;
			if (!result.finished()) match.deadline = System.currentTimeMillis() + 15_000L;
		}
		return snapshot(roomId, match);
	}

	public synchronized Snapshot current(long userId) {
		long roomId = requireRoomId(userId);
		MatchSession match = matches.get(roomId);
		if (match == null) {
			roomService.markWaiting(roomId);
			throw new GameException("MATCH_UNAVAILABLE", "Trận cũ không còn sau khi server khởi động lại.");
		}
		if (!match.engine.isFinished() && System.currentTimeMillis() > match.deadline) resolveTurn(match);
		return snapshot(roomId, match);
	}

	private void resolveTurn(MatchSession match) {
		GameEngine.TurnResult result = match.engine.resolveTimeout();
		if (result != null) {
			match.lastResult = result;
			if (!result.finished()) match.deadline = System.currentTimeMillis() + 15_000L;
		}
	}

	public synchronized Snapshot agreeRematch(long userId) {
		long roomId = requireRoomId(userId);
		MatchSession match = requireMatch(roomId);
		if (!match.engine.isFinished()) throw new GameException("MATCH_NOT_FINISHED", "Chỉ có thể chơi lại sau khi trận kết thúc.");
		if (match.slotOf(userId) == 0) throw new GameException("NOT_IN_MATCH", "Bạn không thuộc trận này.");
		match.rematchVotes.add(userId);
		if (match.rematchVotes.size() == 2) {
			int firstShooter = match.engine.winnerSlot() == null ? 1 : (match.engine.winnerSlot() == 1 ? 2 : 1);
			MatchSession nextMatch = new MatchSession(match.userSlot1, match.userSlot2, new GameEngine(firstShooter), System.currentTimeMillis() + 15_000L);
			matches.put(roomId, nextMatch);
			return snapshot(roomId, nextMatch);
		}
		return snapshot(roomId, match);
	}

	public synchronized Snapshot forfeit(long userId) {
		long roomId = requireRoomId(userId);
		MatchSession match = requireMatch(roomId);
		int slot = match.slotOf(userId);
		if (slot == 0) throw new GameException("NOT_IN_MATCH", "Bạn không thuộc trận này.");
		match.engine.forfeit(slot);
		roomService.leaveAfterForfeit(userId);
		return snapshot(roomId, match);
	}

	public synchronized Snapshot leaveFinishedRoom(long userId) {
		long roomId = requireRoomId(userId);
		MatchSession match = requireMatch(roomId);
		if (!match.engine.isFinished()) throw new GameException("MATCH_NOT_FINISHED", "Chỉ có thể rời phòng tại đây sau khi trận kết thúc.");
		roomService.leaveAfterForfeit(userId);
		return snapshot(roomId, match);
	}

	private long requireRoomId(long userId) {
		RoomService.Snapshot room = roomService.current(userId);
		if (room == null) throw new GameException("NOT_IN_ROOM", "Bạn chưa ở trong phòng.");
		return room.roomId();
	}

	private MatchSession requireMatch(long roomId) {
		MatchSession match = matches.get(roomId);
		if (match == null) throw new GameException("NO_MATCH", "Phòng chưa bắt đầu trận.");
		return match;
	}

	private Snapshot snapshot(long roomId, MatchSession match) {
		int turnNo = match.engine.turnNo();
		return new Snapshot(roomId, turnNo, match.engine.shooterSlot(), match.engine.keeperSlot(),
				match.engine.scoreSlot1(), match.engine.scoreSlot2(), match.deadline, match.engine.isFinished(), match.engine.winnerSlot(),
				match.rematchVotes.size(), match.lastResult == null ? null : match.lastResult.turnNo(),
				match.lastResult == null ? null : match.lastResult.outcome());
	}

	private static class MatchSession {
		private final long userSlot1; private final long userSlot2; private final GameEngine engine; private final Set<Long> rematchVotes = new HashSet<>(); private GameEngine.TurnResult lastResult; private long deadline;
		MatchSession(long userSlot1, long userSlot2, GameEngine engine, long deadline) { this.userSlot1 = userSlot1; this.userSlot2 = userSlot2; this.engine = engine; this.deadline = deadline; }
		int slotOf(long userId) { return userId == userSlot1 ? 1 : userId == userSlot2 ? 2 : 0; }
	}

	public record Snapshot(long roomId, int turnNo, int shooterSlot, int keeperSlot, int scoreSlot1, int scoreSlot2,
			long deadline, boolean finished, Integer winnerSlot, int rematchVotes, Integer lastResolvedTurnNo,
			TurnOutcome lastOutcome) {}
	public static class GameException extends RuntimeException { public final String code; public GameException(String code, String message) { super(message); this.code = code; } }
}

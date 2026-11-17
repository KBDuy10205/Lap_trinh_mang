package penaltygame.game;

/** Luật đá luân lưu thuần Java. Một instance chỉ được sửa bởi luồng xử lý của một trận. */
public class GameEngine {

	private int turnNo = 1;
	private final int firstShooterSlot;
	private int scoreSlot1;
	private int scoreSlot2;
	private Direction shooterChoice;
	private Direction keeperChoice;
	private boolean finished;
	private Integer winnerSlot;

	public GameEngine(int firstShooterSlot) {
		if (firstShooterSlot != 1 && firstShooterSlot != 2) throw new IllegalArgumentException("Slot phải là 1 hoặc 2.");
		this.firstShooterSlot = firstShooterSlot;
	}

	public TurnResult submitChoice(int slot, Direction direction) {
		if (finished) throw new GameException("MATCH_FINISHED", "Trận đã kết thúc.");
		if (slot != shooterSlot() && slot != keeperSlot()) throw new GameException("NOT_IN_MATCH", "Bạn không thuộc trận này.");
		if (direction == null) throw new GameException("INVALID_DIRECTION", "Hướng chọn không hợp lệ.");
		if (slot == shooterSlot()) {
			if (shooterChoice != null) throw new GameException("CHOICE_LOCKED", "Bạn đã xác nhận hướng sút.");
			shooterChoice = direction;
		} else {
			if (keeperChoice != null) throw new GameException("CHOICE_LOCKED", "Bạn đã xác nhận hướng bắt.");
			keeperChoice = direction;
		}
		return shooterChoice != null && keeperChoice != null ? resolve() : null;
	}

	public TurnResult resolveTimeout() {
		if (finished) return null;
		return resolve();
	}

	public void forfeit(int slot) {
		if (slot != 1 && slot != 2) throw new GameException("NOT_IN_MATCH", "Người chơi không thuộc trận này.");
		if (finished) return;
		finished = true;
		winnerSlot = otherSlot(slot);
	}

	private TurnResult resolve() {
		if (finished) return null;
		TurnOutcome outcome;
		if (shooterChoice == null) outcome = TurnOutcome.MISS;
		else if (keeperChoice == null || shooterChoice != keeperChoice) outcome = TurnOutcome.GOAL;
		else outcome = TurnOutcome.SAVED;
		int shooter = shooterSlot();
		if (outcome == TurnOutcome.GOAL) addGoal(shooter);
		TurnResult result = new TurnResult(turnNo, shooter, keeperSlot(), shooterChoice, keeperChoice, outcome,
				scoreSlot1, scoreSlot2, false, null);
		if (shouldFinishAfterThisTurn()) {
			finished = true;
			winnerSlot = scoreSlot1 > scoreSlot2 ? 1 : 2;
			return new TurnResult(result.turnNo(), result.shooterSlot(), result.keeperSlot(), result.shooterDirection(),
					result.keeperDirection(), result.outcome(), scoreSlot1, scoreSlot2, true, winnerSlot);
		}
		turnNo++;
		shooterChoice = null;
		keeperChoice = null;
		return result;
	}

	private boolean shouldFinishAfterThisTurn() {
		if (turnNo < 10) return false;
		if (turnNo == 10) return scoreSlot1 != scoreSlot2;
		return turnNo % 2 == 0 && scoreSlot1 != scoreSlot2;
	}

	private void addGoal(int slot) { if (slot == 1) scoreSlot1++; else scoreSlot2++; }
	public int shooterSlot() { return (turnNo % 2 == 1) ? firstShooterSlot : otherSlot(firstShooterSlot); }
	public int keeperSlot() { return otherSlot(shooterSlot()); }
	private int otherSlot(int slot) { return slot == 1 ? 2 : 1; }
	public int turnNo() { return turnNo; }
	public int scoreSlot1() { return scoreSlot1; }
	public int scoreSlot2() { return scoreSlot2; }
	public boolean isFinished() { return finished; }
	public Integer winnerSlot() { return winnerSlot; }

	public record TurnResult(int turnNo, int shooterSlot, int keeperSlot, Direction shooterDirection,
			Direction keeperDirection, TurnOutcome outcome, int scoreSlot1, int scoreSlot2, boolean finished, Integer winnerSlot) {}
	public static class GameException extends RuntimeException { public final String code; public GameException(String code, String message) { super(message); this.code = code; } }
}

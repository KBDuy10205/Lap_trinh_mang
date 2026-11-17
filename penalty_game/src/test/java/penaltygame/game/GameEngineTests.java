package penaltygame.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GameEngineTests {
	@Test void sameDirectionIsSaved() { GameEngine game = new GameEngine(1); game.submitChoice(1, Direction.LEFT); assertEquals(TurnOutcome.SAVED, game.submitChoice(2, Direction.LEFT).outcome()); }
	@Test void differentDirectionIsGoal() { GameEngine game = new GameEngine(1); game.submitChoice(1, Direction.LEFT); assertEquals(TurnOutcome.GOAL, game.submitChoice(2, Direction.RIGHT).outcome()); }
	@Test void noShooterChoiceIsMiss() { GameEngine game = new GameEngine(1); assertEquals(TurnOutcome.MISS, game.resolveTimeout().outcome()); }
	@Test void noKeeperChoiceIsGoal() { GameEngine game = new GameEngine(1); game.submitChoice(1, Direction.CENTER); assertEquals(TurnOutcome.GOAL, game.resolveTimeout().outcome()); }
	@Test void choiceCannotBeChanged() { GameEngine game = new GameEngine(1); game.submitChoice(1, Direction.LEFT); assertThrows(GameEngine.GameException.class, () -> game.submitChoice(1, Direction.RIGHT)); }
	@Test void firstFivePairsAreNotEndedEarly() { GameEngine game = new GameEngine(1); for (int number = 0; number < 9; number++) { game.submitChoice(game.shooterSlot(), Direction.LEFT); game.submitChoice(game.keeperSlot(), Direction.RIGHT); } assertEquals(10, game.turnNo()); assertNull(game.winnerSlot()); }
}

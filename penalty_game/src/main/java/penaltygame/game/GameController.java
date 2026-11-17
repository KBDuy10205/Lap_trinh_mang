package penaltygame.game;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import penaltygame.config.SessionUser;

@RestController
@RequestMapping("/api/game")
public class GameController {
	private final GameSessionService gameService;
	public GameController(GameSessionService gameService) { this.gameService = gameService; }
	@PostMapping("/start") public ResponseEntity<?> start(HttpServletRequest request) { return run(request, id -> gameService.start(id)); }
	@PostMapping("/choice") public ResponseEntity<?> choice(@RequestBody ChoiceRequest body, HttpServletRequest request) { return run(request, id -> gameService.submit(id, body.direction())); }
	@PostMapping("/rematch") public ResponseEntity<?> rematch(HttpServletRequest request) { return run(request, gameService::agreeRematch); }
	@PostMapping("/forfeit") public ResponseEntity<?> forfeit(HttpServletRequest request) { return run(request, gameService::forfeit); }
	@PostMapping("/leave-finished") public ResponseEntity<?> leaveFinished(HttpServletRequest request) { return run(request, gameService::leaveFinishedRoom); }
	@GetMapping("/current") public ResponseEntity<?> current(HttpServletRequest request) { return run(request, id -> gameService.current(id)); }
	private ResponseEntity<?> run(HttpServletRequest request, Action action) { Long id = SessionUser.getUserId(request); if (id == null) return ResponseEntity.status(401).body(Map.of("code", "UNAUTHENTICATED")); try { return ResponseEntity.ok(action.run(id)); } catch (GameSessionService.GameException | GameEngine.GameException e) { String code = e instanceof GameSessionService.GameException ge ? ge.code : ((GameEngine.GameException)e).code; return ResponseEntity.badRequest().body(Map.of("code", code, "message", e.getMessage())); } }
	private interface Action { Object run(long userId); }
	public record ChoiceRequest(Direction direction) {}
}

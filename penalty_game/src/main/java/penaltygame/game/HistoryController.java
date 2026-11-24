package penaltygame.game;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import penaltygame.config.SessionUser;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

	private final GameSessionService gameService;

	public HistoryController(GameSessionService gameService) {
		this.gameService = gameService;
	}

	@GetMapping
	public ResponseEntity<?> history(HttpServletRequest request) {
		Long userId = SessionUser.getUserId(request);
		if (userId == null) {
			return ResponseEntity.status(401).body(Map.of("code", "UNAUTHENTICATED"));
		}
		return ResponseEntity.ok(gameService.history(userId));
	}
}

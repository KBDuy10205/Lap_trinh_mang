package penaltygame.room;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import penaltygame.config.SessionUser;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

	private final RoomService roomService;

	public RoomController(RoomService roomService) { this.roomService = roomService; }

	@PostMapping
	public ResponseEntity<?> create(HttpServletRequest request) { return run(request, userId -> ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(userId))); }

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody JoinRequest body, HttpServletRequest request) { return run(request, userId -> ResponseEntity.ok(roomService.join(userId, body.roomCode()))); }

	@PostMapping("/ready")
	public ResponseEntity<?> ready(@RequestBody ReadyRequest body, HttpServletRequest request) { return run(request, userId -> ResponseEntity.ok(roomService.setReady(userId, body.ready()))); }

	@PostMapping("/leave")
	public ResponseEntity<?> leave(HttpServletRequest request) { return run(request, userId -> ResponseEntity.ok(roomService.leave(userId))); }

	@GetMapping("/current")
	public ResponseEntity<?> current(HttpServletRequest request) { return run(request, userId -> ResponseEntity.ok(roomService.current(userId))); }

	private ResponseEntity<?> run(HttpServletRequest request, RoomAction action) {
		Long userId = SessionUser.getUserId(request);
		if (userId == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("code", "UNAUTHENTICATED", "message", "Bạn chưa đăng nhập."));
		try { return action.run(userId); }
		catch (RoomService.RoomException exception) { return ResponseEntity.badRequest().body(Map.of("code", exception.code, "message", exception.getMessage())); }
	}

	private interface RoomAction { ResponseEntity<?> run(long userId); }
	public record JoinRequest(String roomCode) {}
	public record ReadyRequest(boolean ready) {}
}

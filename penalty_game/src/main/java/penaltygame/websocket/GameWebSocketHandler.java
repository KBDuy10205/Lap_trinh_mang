package penaltygame.websocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import tools.jackson.databind.json.JsonMapper;
import penaltygame.account.AuthController;

/** Kênh realtime tối giản. Chỉ session HTTP đã đăng nhập mới được giữ kết nối. */
@Component
public class GameWebSocketHandler extends TextWebSocketHandler {

	private final Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final Map<WebSocketSession, Long> roomBySession = new ConcurrentHashMap<>();
	private final JsonMapper jsonMapper;

	public GameWebSocketHandler(JsonMapper jsonMapper) { this.jsonMapper = jsonMapper; }

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws IOException {
		Object value = session.getAttributes().get(AuthController.SESSION_USER_ID);
		if (!(value instanceof Long userId)) { session.close(CloseStatus.NOT_ACCEPTABLE); return; }
		WebSocketSession oldSession = sessions.put(userId, session);
		if (oldSession != null && oldSession.isOpen()) oldSession.close(CloseStatus.NORMAL);
		send(session, Map.of("type", "CONNECTED"));
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		Object value = session.getAttributes().get(AuthController.SESSION_USER_ID);
		if (!(value instanceof Long userId)) { session.close(CloseStatus.NOT_ACCEPTABLE); return; }
		Map<String, Object> payload = jsonMapper.readValue(message.getPayload(), Map.class);
		String type = String.valueOf(payload.get("type"));
		if (!"QUICK_CHAT".equals(type)) { send(session, Map.of("type", "ERROR", "code", "UNSUPPORTED_MESSAGE")); return; }
		String phrase = String.valueOf(payload.get("phraseCode"));
		if (!phrase.matches("(HELLO|GOOD_LUCK|NICE_SHOT|REMATCH)")) { send(session, Map.of("type", "ERROR", "code", "INVALID_CHAT")); return; }
		Long roomId = payload.get("roomId") instanceof Number number ? number.longValue() : null;
		if (roomId == null) { send(session, Map.of("type", "ERROR", "code", "INVALID_ROOM")); return; }
		roomBySession.put(session, roomId);
		broadcastRoom(roomId, Map.of("type", "QUICK_CHAT", "userId", userId, "phraseCode", phrase));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		roomBySession.remove(session);
		Object value = session.getAttributes().get(AuthController.SESSION_USER_ID);
		if (value instanceof Long userId) sessions.remove(userId, session);
	}

	public void broadcast(Map<String, Object> message) {
		for (WebSocketSession session : sessions.values()) { try { if (session.isOpen()) send(session, message); } catch (IOException ignored) { } }
	}

	private void broadcastRoom(long roomId, Map<String, Object> message) {
		roomBySession.forEach((session, sessionRoomId) -> {
			if (!sessionRoomId.equals(roomId)) return;
			try {
				if (session.isOpen()) send(session, message);
			} catch (IOException ignored) {
			}
		});
	}

	private void send(WebSocketSession session, Map<String, Object> message) throws IOException { session.sendMessage(new TextMessage(jsonMapper.writeValueAsString(message))); }
}

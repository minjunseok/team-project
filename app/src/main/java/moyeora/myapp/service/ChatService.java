package moyeora.myapp.service;

import moyeora.myapp.vo.ChatMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface ChatService {

    List<ChatMessage> findChatMessageBySchoolNo(int schoolNo);
    <T> void sendMessage(WebSocketSession session, T message);
}

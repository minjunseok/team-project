package moyeora.myapp.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moyeora.myapp.service.ChatService;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Data
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;

    private int gm_no;
    private int schoolNo;
    private int sender; // 메세지 보낸 사람
    private String message; // 메세지
    private String photo;

    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatMessage(int schoolNo, int sender, String message) {
        this.schoolNo = schoolNo;
        this.sender = sender;
        this.message = message;
    }

    public void handleActions(WebSocketSession session, ChatMessage chatMessage, ChatService chatService) {
        if ( chatMessage.getType().equals(ChatMessage.MessageType.ENTER) ) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    public <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream().forEach(session -> chatService.sendMessage(session, message));
    }
}

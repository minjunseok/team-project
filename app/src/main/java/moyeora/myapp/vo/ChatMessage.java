package moyeora.myapp.vo;

import lombok.Data;

@Data
public class ChatMessage {
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    private int roomNo; // 채팅방번호 ( dm_no, gm_no)
    private int sender; // 메세지 보낸 사람
    private String message; // 메세지
}

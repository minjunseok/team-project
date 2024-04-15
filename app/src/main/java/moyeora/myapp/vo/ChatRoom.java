package moyeora.myapp.vo;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoom {
    private int roomNo;
    private int schoolNo;
    //private String name;
    private Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(int roomNo, int schoolNo) {
        this.roomNo = roomNo;
        this.schoolNo = schoolNo;
    }

    //수정중
}

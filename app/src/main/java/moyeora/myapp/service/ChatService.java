package moyeora.myapp.service;

import moyeora.myapp.vo.ChatRoom;

import java.util.List;

public interface ChatService {
    List<ChatRoom> list();
    ChatRoom add(ChatRoom chatRoom);

}

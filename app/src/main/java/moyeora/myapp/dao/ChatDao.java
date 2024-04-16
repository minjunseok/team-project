package moyeora.myapp.dao;

import moyeora.myapp.vo.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatDao {

    void addChatMessage(ChatMessage chatMessage);
    List<ChatMessage> findChatMessageByChatRoomNo(int roomNo);
}

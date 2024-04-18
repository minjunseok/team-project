package moyeora.myapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moyeora.myapp.dao.ChatDao;
import moyeora.myapp.service.ChatService;
import moyeora.myapp.vo.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultChatService implements ChatService {

    @Autowired
    private ChatDao dao;

    private final ObjectMapper objectMapper;


    @Override
    public List<ChatMessage> findChatMessageBySchoolNo(int schoolNo) {
        return dao.findChatMessageByChatRoomNo(schoolNo);
    }

    @Override
    public <T> void sendMessage(WebSocketSession session, T message) {
        try {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
            dao.addChatMessage(new ChatMessage()); // *** ChatMessage Date Set
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}

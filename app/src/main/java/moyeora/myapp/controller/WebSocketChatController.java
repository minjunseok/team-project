package moyeora.myapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moyeora.myapp.service.ChatService;
import moyeora.myapp.vo.ChatMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketChatController extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws  Exception {
        String payload = message.getPayload();
        log.info("payload {} ", payload);

        //message add
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

    }
}

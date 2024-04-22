package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.impl.DefaultChatService;
import moyeora.myapp.vo.Gm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private static final Log log = LogFactory.getLog(ChatController.class);
    private final SimpMessageSendingOperations operations;
    private final DefaultChatService chatService;

    @GetMapping("chatTest")
    public String chatTestForm() {
        return "/chat/form";
    }

    @MessageMapping("/gm")
    public void send(Gm gm) {
        operations.convertAndSend
                ("/sub/gm/" + gm.getSchoolNo(), gm);
        log.info("메세지 전송 성공");
        log.info(gm.toString());
        chatService.save(gm);
    }
}

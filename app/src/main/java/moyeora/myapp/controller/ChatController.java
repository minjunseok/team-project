package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.ChatService;
import moyeora.myapp.vo.ChatMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {

    private static final Log log = LogFactory.getLog(ChatController.class);

    private final ChatService chatService;

    @GetMapping("gm")
    public void getGm(int schoolNo, Model model) {
        List<ChatMessage> chat = chatService.findChatMessageBySchoolNo(schoolNo);
        model.addAttribute("chat", chat);

    }
}

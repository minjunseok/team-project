package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.UserService;
import moyeora.myapp.service.impl.DefaultChatService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.Dm;
import moyeora.myapp.vo.DmRoom;
import moyeora.myapp.vo.Gm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private static final Log log = LogFactory.getLog(ChatController.class);
    private final SimpMessageSendingOperations operations;
    private final DefaultChatService chatService;
    private final UserService userService;
    private final FileUploadHelper storageService;

    private String gmUploadDir = "gm/";
    private String dmUploadDir = "dm/";

    @Value("${ncp.storage.bucket}")
    private String bucketname;

    @GetMapping("chatTest")
    public String chatTestForm(Model model, int sender, int receiver) {
        model.addAttribute("sender", userService.get(sender));
        model.addAttribute("receiver", userService.get(receiver));
        return "/chat/test";
    }

    @GetMapping("gm")
    public String gmForm(Model model, int schoolNo, int sender) {
        model.addAttribute("schoolNo", schoolNo);
        model.addAttribute("sender", userService.getUserInfo(sender));
        System.out.println("****************" + userService.getUserInfo(sender) + schoolNo + sender);
        model.addAttribute("chatList", chatService.getGmList(schoolNo));
        return "chat/gm";
    }

    @GetMapping("dm")
    public String dmForm(Model model, int sender, int receiver) {
        DmRoom room = chatService.getDmRoom(sender, receiver);

        if (room == null) {
            room = new DmRoom(sender, receiver);
            chatService.addDmRoom(room);
            room = chatService.getDmRoom(room.getNo());
        }
        log.info("room : " + room.toString());

        model.addAttribute("sender", userService.get(sender));
        model.addAttribute("receiver", userService.get(receiver));
        model.addAttribute("room", room);
        model.addAttribute("chatList", chatService.getDmList(room.getNo()));
        return "/chat/dm";
    }

    @MessageMapping("/gm")
    public Gm publishGm(Gm gm) {
        operations.convertAndSend
                ("/sub/gm/" + gm.getSchoolNo(), gm);
        log.info("메세지 전송 성공");
        return gm;
    }

    @PostMapping("addGm")
    @ResponseBody
    public Gm addGm(
            @RequestPart(value = "gm") Gm gm,
            @RequestParam(value = "photo", required = false) MultipartFile[] photos) throws Exception {

        if (photos != null) {
            ArrayList<String> files = new ArrayList<>();
            for (MultipartFile file : photos) {
                if (file.getSize() == 0) {
                    continue;
                }
                String filename = storageService.upload(this.bucketname, this.gmUploadDir, file);
                files.add(filename);
                gm.setPhoto(filename);
            }
        }

        log.info("gm(+photo) : " + gm);
        chatService.saveGm(gm);
        System.out.println("@@@@@@@@@GM" + gm);
        return gm;
    }

    @ResponseBody
    @PostMapping("addDm")
    public Dm addDm(
            @RequestPart(value = "dm") Dm dm,
            @RequestParam(value = "photo", required = false) MultipartFile[] photos) throws Exception {

        if (photos != null) {
            ArrayList<String> files = new ArrayList<>();
            for (MultipartFile file : photos) {
                if (file.getSize() == 0) {
                    continue;
                }
                String filename = storageService.upload(this.bucketname, this.dmUploadDir, file);
                files.add(filename);
                dm.setPhoto(filename);
            }
        }

        log.info("dm : " + dm);
        chatService.saveDm(dm);
        return dm;
    }

    @MessageMapping("/dm")
    public void publishDm(Dm dm) {
        operations.convertAndSend
                ("/sub/dm/" + dm.getRoomNo(), dm);
        log.info("메세지 전송 성공");
        log.info(dm.toString());
    }
}
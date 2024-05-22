package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.service.impl.DefaultChatService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.Dm;
import moyeora.myapp.vo.DmRoom;
import moyeora.myapp.vo.Gm;
import moyeora.myapp.vo.User;
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
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private static final Log log = LogFactory.getLog(ChatController.class);
    private final SimpMessageSendingOperations operations;
    private final DefaultChatService chatService;
    private final UserService userService;
    private final FileUploadHelper storageService;
    private final SchoolService schoolService;

    @Value("${ncp.storage.bucket}")
    private String bucketname;
    @Value("${ncp.storage.endpoint}")
    private String endpoint;

    private String gmUploadDir = "gm/";
    private String dmUploadDir = "dm/";

    @GetMapping("chatTest")
    public String chatTestForm(Model model, int sender, int receiver) {
        model.addAttribute("sender",userService.get(sender));
        model.addAttribute("receiver",userService.get(receiver));
        return "/chat/test";
    }

    @GetMapping("gm")
    public String gmForm(Model model, int schoolNo, @LoginUser User loginUser) {
        model.addAttribute("school", schoolService.get(schoolNo));
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("chatList", chatService.getGmList(schoolNo));
        return "chat/gm";
    }

    @GetMapping("dm")
    public String dmForm(Model model, int receiver, @LoginUser User loginUser) {
        DmRoom room = chatService.getDmRoom(loginUser.getNo(), receiver);
        if (room == null) {
            room = new DmRoom(loginUser.getNo(), receiver);
            chatService.addDmRoom(room);
            room = chatService.getDmRoom(room.getNo());
        }
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("receiver", userService.get(receiver));
        model.addAttribute("room", room);
        model.addAttribute("chatList", chatService.getDmList(room.getNo()));
        return "chat/dm";
    }

    @MessageMapping("/gm")
    public Gm publishGm(Gm gm) {
        operations.convertAndSend
                ("/sub/gm/" + gm.getSchool().getNo(), gm);
        log.info("메세지 전송 성공");
        return gm;
    }

    @PostMapping("addGm")
    @ResponseBody
    public Gm addGm(
            @RequestPart(value = "gm") Gm gm,
            @RequestParam(value = "photo",required = false) MultipartFile[] photos) throws Exception {
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
        chatService.saveGm(gm);
        gm = chatService.getGm(gm.getNo());
        log.debug("getGm = " + gm.toString());
        return gm;
    }


    @PostMapping("addDm")
    @ResponseBody
    public Dm addDm(
            @RequestPart(value = "dm") Dm dm,
            @RequestParam(value = "photo",required = false) MultipartFile[] photos) throws Exception {
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
        chatService.saveDm(dm);
        User sender = userService.getUserInfo(dm.getSender().getNo());
        User receiver = userService.getUserInfo(dm.getReceiver().getNo());
        System.out.println("sender" + sender.toString());
        System.out.println("receiver" + receiver.toString());
        dm = chatService.getDm(dm.getNo());
        dm.setReceiver(receiver);
        dm.setSender(sender);
        System.out.println("dm" + dm);

        return dm;
    }

    @MessageMapping("/dm")
    public void publishDm(Dm dm) {
        operations.convertAndSend
                ("/sub/dm/" + dm.getRoomNo(), dm);
        log.info("메세지 전송 성공");
    }

    @GetMapping("gmListOnlyLast")
    @ResponseBody
    public List<Gm> GmListOnlyLast(int no) {
        List<Gm> list = chatService.getGmListOnlyLast(no);
        String ncdPath = this.endpoint + "/" + this.bucketname;
        for (Gm gm : list) {
            gm.setFilePath(ncdPath + "/" + gmUploadDir);
        }
        return list;
    }

/*    @GetMapping("dmListOnlyLast")
    @ResponseBody
    public List<Dm> DmListOnlyLast(int no) {
        List<Dm> list = chatService.getDmListOnlyLast(no);
        String ncdPath = this.bucketname + "/" + this.endpoint;
        for (Dm dm : list) {
            User receiver = userService.getUserInfo(dm.getReceiver().getNo());
            dm.setReceiver(receiver);
            dm.setFilePath(ncdPath + "/" + dmUploadDir);
        }
        return list;
    }*/

    @GetMapping("dmListOnlyLast")
    @ResponseBody
    public List<Dm> DmListOnlyLast(int no) {
        List<Dm> list = chatService.getDmListOnlyLast(no);
        String ncdPath = this.bucketname + "/" + this.endpoint;
        for (Dm dm : list) {
            int num = (dm.getReceiver().getNo() != no) ? dm.getReceiver().getNo() : dm.getUserNoDm();
            User receiver = userService.getUserInfo(num);
            dm.setReceiver(receiver);
            dm.setFilePath(ncdPath + "/" + dmUploadDir);
        }
        return list;
    }
}
package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.security.util.RedisUtil;
import moyeora.myapp.service.TagService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.service.impl.DefaultMailService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.User;
import moyeora.myapp.vo.UserTag;
import moyeora.myapp.vo.role.Role;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean {
  private static final Log log = LogFactory.getLog(UserController.class);
  private final UserService userService;
  private final TagService tagService;
  private final FileUpload fileUpload;
    private final DefaultMailService mailService;
    private final RedisUtil redisUtil;
    private final String uploadDir = "user/";
  @Value("${ncp.storage.bucket}") private String bucket;



    @Override
    public void afterPropertiesSet() throws Exception {

        log.debug(String.format("uploadDir: %s", this.uploadDir));
    }

    @GetMapping("form")
    public void form(Model model) throws Exception{

        model.addAttribute("tags",tagService.findAllTag());
    }

    @PostMapping("add")
    public String add(User user, MultipartFile file) throws Exception{
        System.out.println(user);

        if(file.getSize() > 0){
            String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
            user.setPhoto(filename);
        }
        user.setRole(Role.USER.getKey());
        userService.add(user);

        return "redirect:index";
    }

    @GetMapping("view")
    public void view(Model model) throws Exception {
        User user = userService.get(59);

        System.out.println("============>");
        System.out.println(user);

        List<UserTag> userTags = user.getTags();
        HashMap<Integer,UserTag> userTagMap = new HashMap<>();
        for (UserTag userTag : userTags) {
            userTagMap.put(userTag.getTagNo(), userTag);
        }

        System.out.println(user);
        model.addAttribute("user",user);
        model.addAttribute("userTagMap",userTagMap);
        model.addAttribute("tags",tagService.findAllTag());
        for(UserTag tag : user.getTags()) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@"+tag.getTagNo());
        }
    }

@PostMapping("update")
public String update(User user, MultipartFile file) throws Exception {

    User old = userService.get(59);
    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$" + old);
    System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$" + old.getNo());
    if (old == null) {
        throw new Exception("회원 번호가 유효하지 않습니다.");
    }
    user.setNo(old.getNo());

    user.setCreatedAt(old.getCreatedAt());


    if(file.getSize() > 0){
        String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
        user.setPhoto(filename);
        fileUpload.delete(this.bucket, this.uploadDir, old.getPhoto());
    } else {
        user.setPhoto(old.getPhoto());
    }

    userService.update(user);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ user);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ userService);
    return "redirect:/home";
    }

    @PostMapping("passwordUpdate")
    @ResponseBody
    public String update(@RequestBody String password, int no) throws Exception {
        User user = new User();
        user.setPassword(password);
        user.setNo(no);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + password);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ userService);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + userService.passwordUpdate(user));
        userService.passwordUpdate(user);

        return "redirect:index";
    }


    @PostMapping("sendEmail")
    public String sendEmail(String email, Model model) throws Exception {

        String authId = doSend(email, "[moyeora] authentication code", createCode(),
                createAuthId(email), "form");
        model.addAttribute("authId", authId);
        model.addAttribute("status", "sent");
        redisUtil.setDataExpire(authId + "_e", email, 60 * 5L);

        return "redirect:/user/form";
    }


    private String doSend(String email, String subject, String code, String authId, String template)
            throws MessagingException {
        mailService.sendEmail(email, subject, code, authId, template);
        redisUtil.setDataExpire(authId, code, 60 * 5L);
        return authId;
    }

    @PostMapping("verifyCode")
    public String verifyCode(String email, String code, String authId, Model model)
            throws Exception {
        String savedCode = (String) redisUtil.getData(authId);
        if (savedCode == null) {
            model.addAttribute("status", "savedCode == null");
        } else if (!savedCode.equals(code)) {
            model.addAttribute("status", "savedCode != code");
        }

        return "/user/form";
    }

    private String createAuthId(String email) throws NoSuchAlgorithmException { // 이메일로 항상 유지되는 키값으로 사용할것.
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(email.getBytes());
        md.update(LocalDateTime.now().toString().getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : md.digest()) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    private String createCode() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 12;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

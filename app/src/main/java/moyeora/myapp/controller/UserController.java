package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean {

    private static final Log log = LogFactory.getLog(UserController.class);
    private final UserService userService;
    private String uploadDir;


    @Override
    public void afterPropertiesSet() throws Exception {
        this.uploadDir = "user/";
        log.debug(String.format("uploadDir: %s", this.uploadDir));

    }

    @GetMapping("form")
    public void form() throws Exception{

    }


    @PostMapping("add")
    public String add(User user, MultipartFile file) throws Exception{
//        if (file.getSize() > 0) {
//            String filename = UUID.upload(this.uploadDir, file);
//            user.setPhoto(filename);
//        }
        userService.add(user);
        return "redirect:list";
    }

    @GetMapping("list")
    public void list(Model model) throws Exception {
        model.addAttribute("list", userService.list());
    }

    @GetMapping("view")
    public void view(int no, Model model) throws Exception {
        User user = userService.get(no);
        if (user == null) {
            throw new Exception("회원 번호가 유효하지 않습니다.");
        }
        model.addAttribute("user", user);
    }

    @PostMapping("update")
    public String update(User user, MultipartFile file) throws Exception {

        User old = userService.get(user.getNo());
        if (old == null) {
            throw new Exception("회원 번호가 유효하지 않습니다.");
        }
        user.setCreatedAt(old.getCreatedAt());
//
//        if (file.getSize() > 0) {
//            String filename = storageService.upload(this.uploadDir, file);
//            user.setPhoto(filename);
//            storageService.delete( this.uploadDir, old.getPhoto());
//        } else {
//            user.setPhoto(old.getPhoto());
//        }

        userService.update(user);
        return "redirect:list";
    }

    @GetMapping("delete")
    public String delete(int no) throws Exception {
        User user = userService.get(no);
        if (user == null) {
            throw new Exception("회원 번호가 유효하지 않습니다.");
        }

        userService.delete(no);

//        String filename = user.getPhoto();
//        if (filename != null) {
//            storageService.delete( this.uploadDir, user.getPhoto());
//        }
        return "redirect:list";
    }

}

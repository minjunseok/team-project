package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.TagService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean {
  private static final Log log = LogFactory.getLog(UserController.class);
  private final UserService userService;
  private final TagService tagService;
  private final FileUpload fileUpload;
  private final String uploadDir =  "user/";;
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


        if(file.getSize() > 0){
            System.out.println("add 2 실행@@@@@@@@@@@@@@@");
            String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
            System.out.println("add 3 실행@@@@@@@@@@@@@@@" + user);
            user.setPhoto(filename);
        }
        userService.add(user);

        return "redirect:add";
    }

    @GetMapping("view")
    public void view(Model model) throws Exception{
        User user = userService.get(38);

        model.addAttribute("user",user);
        model.addAttribute("tags",tagService.findAllTag());
    }
}

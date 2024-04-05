package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.StorageService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController implements InitializingBean {

  private static final Log log = LogFactory.getLog(UserController.class);

  private final UserService userService;
  private final StorageService storageService;

  private  String uploadDir;

  @Value("${ncp.storage.bucket}")
  private String bucketName;

  @Override
  public void afterPropertiesSet() throws Exception {
    this.uploadDir = "user/";

    log.debug(String.format("uploadDir: %s", this.uploadDir));
    log.debug(String.format("bucketName: %s", this.bucketName));
  }

  @GetMapping("form")
  public void form() throws Exception {
  }

  @PostMapping("add")
  public String add(User user, MultipartFile file) throws Exception {
    if (file.getSize() > 0) {
      String filename = storageService.upload(this.bucketName, this.uploadDir, file);
      user.setPhoto(filename);
    }
    userService.add(user);
    return "redirect:list";
  }

  @GetMapping("list")
  public void list(
          @RequestParam(defaultValue = "1") int pageNo,
          @RequestParam(defaultValue = "3") int pageSize,
          Model model) throws Exception {

    if (pageSize < 3 || pageSize > 20) {
      pageSize = 3;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = userService.countAll();
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize > 0 ? 1 : 0));

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("list", userService.list(pageNo, pageSize));
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }

  @GetMapping("view")
  public void view(int userNo, Model model) throws Exception {
    User user = userService.get(userNo);
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

    if (file.getSize() > 0) {
      String filename = storageService.upload(this.bucketName, this.uploadDir, file);
      user.setPhoto(filename);
      storageService.delete(this.bucketName, this.uploadDir, old.getPhoto());
    } else {
      user.setPhoto(old.getPhoto());
    }

    userService.update(user);
    return "redirect:list";
  }

   @GetMapping("delete")
  public String delete(int userNo) throws Exception {
    User user = userService.get(userNo);
    if (user == null) {
      throw new Exception("회원 번호가 유효하지 않습니다.");
    }

    userService.delete(userNo);

    String filename = user.getPhoto();
    if (filename != null) {
      storageService.delete(this.bucketName, this.uploadDir, user.getPhoto());
    }
    return "redirect:list";
  }
}

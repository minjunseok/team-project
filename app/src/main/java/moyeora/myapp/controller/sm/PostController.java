package moyeora.myapp.controller.sm;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.PostService;
import moyeora.myapp.service.StorageService;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.PostCategory;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/post")
public class PostController {

  private static final Log log = LogFactory.getLog(PostController.class);
  private final PostService postService;
  private final StorageService storageService;
  private String uploadDir = "post/";

  @Value("${ncp.ss.bucketname}")
  private String bucketName;


  @GetMapping("form")
  public void form(PostCategory categoryNo, Model model) throws Exception {
    model.addAttribute("categoryNo", categoryNo);
  }

  @PostMapping("add")
  public String add(
          Post post,
          PostCategory category,
          MultipartFile[] attachedFiles,
          HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }
    post.setUserNo(loginUser);

    ArrayList<AttachedFile> files = new ArrayList<>();
    if (post.getCategoryNo().getNo() == 1) {
      for (MultipartFile file : attachedFiles) {
        if (file.getSize() == 0) {
          continue;
        }
        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
        files.add(AttachedFile.builder().filePath(filename).build());
      }
    }
    if (files.size() > 0) {
      post.setFileList(files);
    }

    postService.add(post);

    return "redirect:list";
  }

  @GetMapping("list")
  public void list(
          PostCategory categoryNo,
          @RequestParam(defaultValue = "1") int pageNo,
          @RequestParam(defaultValue = "3") int pageSize,
          Model model) throws Exception {

    if (pageSize < 3 || pageSize > 20) {
      pageSize = 3;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = postService.countAll(categoryNo);
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize) > 0 ? 1 : 0);

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("categoryNo", categoryNo);
    model.addAttribute("list", postService.list(categoryNo, pageNo, pageSize));
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }

  @GetMapping("view")
  public void view(int categoryNotice,int categoryRegular, int no, Model model) throws Exception {
    Post post = postService.get(no);
    if (post == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }

    model.addAttribute("categoryNotice", categoryNotice);
    model.addAttribute("categoryRegular", categoryRegular);
    model.addAttribute("post", post);
  }

  @PostMapping("update")
  public String update(
          Post post,
          MultipartFile[] attachedFiles,
          HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    Post old = postService.get(post.getNo());
    if (old == null) {
      throw new Exception("번호가 유효하지 않습니다.");

    } else if (old.getNo() != loginUser.getUserNo()) {
      throw new Exception("권한이 없습니다.");
    }

    ArrayList<AttachedFile> files = new ArrayList<>();
    if (post.getCategoryNo().getNo() == 1) {
      for (MultipartFile file : attachedFiles) {
        if (file.getSize() == 0) {
          continue;
        }
        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
        files.add(AttachedFile.builder().filePath(filename).build());
      }
    }
    if (files.size() > 0) {
      post.setFileList(files);
    }

    postService.update(post);

    return "redirect:list";

  }

  @GetMapping("delete")
  public String delete(int categoryNotice, int categoryRegular, int no, HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    Post post = postService.get(no);
    if (post == null) {
      throw new Exception("번호가 유효하지 않습니다.");

    } else if (post.getNo() != loginUser.getUserNo()) {
      throw new Exception("권한이 없습니다.");
    }

    List<AttachedFile> files = postService.getAttachedFiles(no);

    postService.delete(no);

    for (AttachedFile file : files) {
      storageService.delete(this.bucketName, this.uploadDir, file.getFilePath());
    }

    return "redirect:list";
  }

  @GetMapping("file/delete")
  public String fileDelete(int categoryNotice, int categoryRegular, int no, HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    AttachedFile file = postService.getAttachedFile(no);
    if (file == null) {
      throw new Exception("첨부파일 번호가 유효하지 않습니다.");
    }

    User writer = postService.get(file.getPostNo()).getUserNo();
    if (writer.getUserNo() != loginUser.getUserNo()) {
      throw new Exception("권한이 없습니다.");
    }

    postService.deleteAttachedFile(no);

    storageService.delete(this.bucketName, this.uploadDir, file.getFilePath());

    return "redirect:../view?category=" + categoryNotice + categoryRegular + "&no=" + file.getPostNo();
  }
}
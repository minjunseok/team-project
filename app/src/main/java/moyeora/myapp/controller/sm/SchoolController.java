package moyeora.myapp.controller.sm;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.StorageService;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.School;
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
@RequestMapping("/board")
public class SchoolController {

  private final SchoolService schoolService;
  private final StorageService storageService;

  private String uploadDir = "school/";


  @Value("${$npc.ss.bucketname}")
  private String bucketName;

  @GetMapping("form")
  public void form(int no, Model model) throws Exception {
    model.addAttribute("schoolName", no == 1 ? "게시글" : "일정");
    model.addAttribute("category_no" , no);
  }

  @PostMapping("add")
  public String add(
      School school,
      MultipartFile[] attachedFiles,
      HttpSession session,
      Model model) throws Exception {

    model.addAttribute("categroy_no", school.getCategory());

    Member loginUser = (Member) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }
    school.setWriter(loginUSer);

    ArrayList<AttachedFile> files = new ArrayList<>();
    if (school.getCategory() == 1) {
      for (MultipartFile file : attachedFiles) {
        if(file.getSize() == 0) {
          continue;
        }
        String filename = storageService.upload(this.bucketName, this.uploadDir, file);
        files.add(AttachedFile.builder().filePath(filename).build());
      }
    }
  if (files.size() > 0) {
    school.setFile(files);
  }

    schoolService.add(school);

    return "redirect:list";

  }

  @GetMapping("List")
  public void list(
          int no,
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "3") int pageSize,
      Model model) throws Exception {
    if (pageSize < 3 || pageSize > 20) {
      pageSize = 3;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = schoolService.countAll(no);
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize > 0 ? 1 : 0));

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("schoolName", no == 1 ? "게시글" : "일정");
    model.addAttribute("category_no", no);
    model.addAttribute("list", schoolService.list(no, pageNo, pageSize));

  }
  )




}

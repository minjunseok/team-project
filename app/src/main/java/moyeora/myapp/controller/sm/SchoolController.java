package moyeora.myapp.controller.sm;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.StorageService;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.User;
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
@RequestMapping("/school")
public class SchoolController {

  private final SchoolService schoolService;
  private final StorageService storageService;

  private String uploadDir = "school/";


  @Value("${$npc.ss.bucketname}")
  private String bucketName;

  @GetMapping("form")
  public void form() throws Exception {

  }

  @PostMapping("add")
  public String add(
      School school,
      HttpSession session,
      Model model) throws Exception {

    model.addAttribute("category_no", school.getCategoryNo());

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }
    school.setWriter(loginUSer);

    schoolService.add(school);

    return "redirect:list";

  }

  @GetMapping("List")
  public void list(
          int categoryNo,
      @RequestParam(defaultValue = "1") int pageNo,
      @RequestParam(defaultValue = "3") int pageSize,
      Model model) throws Exception {
    if (pageSize < 3 || pageSize > 20) {
      pageSize = 3;
    }

    if (pageNo < 1) {
      pageNo = 1;
    }

    int numOfRecord = schoolService.countAll(categoryNo);
    int numOfPage = numOfRecord / pageSize + ((numOfRecord % pageSize > 0 ? 1 : 0));

    if (pageNo > numOfPage) {
      pageNo = numOfPage;
    }

    model.addAttribute("category", categoryNo);
    model.addAttribute("list", schoolService.list(categoryNo, pageNo, pageSize));
    model.addAttribute("pageNo", pageNo);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("numOfPage", numOfPage);
  }


 @GetMapping("view")
  public void view(int categoryNo, int postNo, Model model) throws Exception {
    School school = schoolService.get(postNo);
    if (school == null) {
      throw new Exception("번호가 유효하지 않습니다.");
    }
    model.addAttribute("category", categoryNo);
    model.addAttribute("school", school);
  }

  @PostMapping("update")
  public String update(
          School school,
          HttpSession session,
          Model model) throws Exception {

    model.addAttribute("category", school.getCategoryNo());

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    School old = schoolService.get(school.getNo()); // postNo 아니면 categoryNo 의 정보를 받아와야 하는데 어떻게...?
    if (old == null) {
      throw new Exception("번호가 유효하지 않습니다.");

    } else if (old.getWriter().getNo() != loginUser.getNo()) { // 위 주석과 동일
      throw new Exception("권한이 없습니다.");
    }

    schoolService.update(school);

    return "redirect:list";

  }

  @GetMapping("delete")
  public String delete(int categoryNo, int postNo, HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      throw new Exception("로그인하시기 바랍니다!");
    }

    School school = schoolService.get(postNo);
    if (school == null) {
      throw new Exception("번호가 유효하지 않습니다.");

    } else if (school.getWriter().getNo() != loginUser.getNo()) { // 위 주석과 동일
      throw new Exception("권한이 없습니다.");
    }

    schoolService.delete(postNo);

    return "redirect:list?category=" + categoryNo;
  }


  // 저희 스쿨 메인에는 필요가 없는 부분인 것 같아서 주석 처리 했습니다!

//  @GetMapping("file/delete")
//  public String fileDelete(int no, int pid_no, HttpSession session) throws Exception {
//
//    User loginUser = (User) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      throw new Exception("로그인하시기 바랍니다!");
//    }
//
//    User writer = schoolService.get(file.getBoardNo()).getWriter();
//    if (writer.getNo() != loginUser.getNo()) {
//      throw new Exception("권한이 없습니다.");
//    }
//
//    storageService.delete(this.bucketName, this.uploadDir, file.getFilePath());
//
//    return "redirect:../view?category=" + no + "&pid_no=" + file.getBoardNo();
//  }

}

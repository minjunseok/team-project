package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.dto.school.admin.SchoolOpenRangeUpdateRequestDTO;
import moyeora.myapp.dto.schoolclass.ClassDeleteDTO;
import moyeora.myapp.service.SchoolAdminService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.TagService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolTag;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@RequestMapping("school/admin")
public class SchoolAdminController {
    private final SchoolAdminService schoolAdminService;
    private final TagService tagService;
    private final SchoolService schoolService;
    private final FileUpload fileUpload;
    private final String uploadDir = "school/";
    private final static Log log = LogFactory.getLog(SchoolAdminController.class);

    @Value("${ncp.storage.bucket}")
    private String bucketName;


    @GetMapping
    public String setting(int schoolNo, Model model, @LoginUser User loginUser) {
       if (schoolAdminService.authSubAdmin(loginUser.getNo(), schoolNo) < 1) {
           System.out.println("schooladmin");
        return "redirect:/index";
        }   
        School school = schoolAdminService.getSchool(schoolNo);
        List<SchoolTag> schooltags = school.getTags();
        HashMap<Integer, SchoolTag> schoolTagMap = new HashMap<>();
        for (SchoolTag schoolTag : schooltags) {
            schoolTagMap.put(schoolTag.getTagNo(), schoolTag);
        }

        model.addAttribute("schoolTagMap", schoolTagMap);
        model.addAttribute("tags", tagService.findAllTag());
        return "school/admin";
    }

  @GetMapping("userList")
  @ResponseBody
  public ResponseEntity<List<SchoolUser>> userList(int schoolNo, @LoginUser User loginUser) {
    if (schoolAdminService.authAdmin(loginUser.getNo(), schoolNo) < 1) {
      return ResponseEntity.status(401).build();
    }
      return ResponseEntity.status(200).body(schoolAdminService.findUserBySchoolNo(schoolNo, 2));
    }


  @GetMapping("sub/blackList")
  public ResponseEntity<List<SchoolUser>> blackList(int schoolNo, @LoginUser User loginUser) {
    if (schoolAdminService.authSubAdmin(loginUser.getNo(), schoolNo) < 1) {
      return ResponseEntity.status(401).build();
    }
      return ResponseEntity.status(200).body(schoolAdminService.findUserBySchoolNo(schoolNo, 1));

    }


  @GetMapping("sub/submitList")
  public ResponseEntity<List<SchoolUser>> submitList(int schoolNo, @LoginUser User loginUser) {
    if (schoolAdminService.authSubAdmin(loginUser.getNo(), schoolNo) < 1) {
      return ResponseEntity.status(401).build();
    }
      return ResponseEntity.status(200).body(schoolAdminService.findUserBySchoolNo(schoolNo, 5));

    }


  @PostMapping("sub/approve")
  public ResponseEntity<?> memberApprove(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO, @LoginUser User loginUser) {
    if (schoolAdminService.authSubAdmin(loginUser.getNo(), memberUpdateRequestDTO.getSchoolNo()) < 1) {
      return ResponseEntity.status(401).build();
    }
      memberUpdateRequestDTO.setLevelNo(2);
      schoolAdminService.approveUpdate(memberUpdateRequestDTO);
      return ResponseEntity.status(200).build();
    }

    @PostMapping("sub/reject")
    public ResponseEntity<?> memberReject(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO, @LoginUser User loginUser) {
        if (schoolAdminService.authSubAdmin(loginUser.getNo(), memberUpdateRequestDTO.getSchoolNo()) < 1) {
            return ResponseEntity.status(401).build();
        }
        System.out.println(memberUpdateRequestDTO + "@@@@@@");
        schoolAdminService.deleteMember(memberUpdateRequestDTO);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("sub/blackAdd")
    public ResponseEntity<?> blackAdd(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO, @LoginUser User loginUser) {
        if (schoolAdminService.authSubAdmin(loginUser.getNo(), memberUpdateRequestDTO.getSchoolNo()) < 1) {
            return ResponseEntity.status(401).build();
        }
        memberUpdateRequestDTO.setLevelNo(1);
        schoolAdminService.blackUpdate(memberUpdateRequestDTO);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("sub/blackDelete")
    public ResponseEntity<?> blackDelete(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO, @LoginUser User loginUser) {
        if (schoolAdminService.authSubAdmin(loginUser.getNo(), memberUpdateRequestDTO.getSchoolNo()) < 1) {
            return ResponseEntity.status(401).build();
        }
        memberUpdateRequestDTO.setLevelNo(2);
        schoolAdminService.blackUpdate(memberUpdateRequestDTO);
        return ResponseEntity.status(200).build();
    }

    @PostMapping("levelUpdate")
    public ResponseEntity<Integer> memberLevelUpdate(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO, @LoginUser User loginUser) {
        if (schoolAdminService.authAdmin(loginUser.getNo(), memberUpdateRequestDTO.getSchoolNo()) < 1) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(schoolAdminService.levelUpdate(memberUpdateRequestDTO));
    }






    @PostMapping("openClosed")
    public ResponseEntity<?> openClosed(@RequestBody SchoolOpenRangeUpdateRequestDTO schoolOpenRangeUpdateRequestDTO, @LoginUser User loginUser) {
        if (schoolAdminService.authAdmin(loginUser.getNo(), schoolOpenRangeUpdateRequestDTO.getSchoolNo()) < 1) {
            return ResponseEntity.status(401).build();
        }
        schoolAdminService.updateSchoolOpenRange(schoolOpenRangeUpdateRequestDTO);
        return ResponseEntity.status(200).build();
    }

    @GetMapping("openClosedCheck")
    @ResponseBody
    public ResponseEntity<School> openClosedCheck(int schoolNo, @LoginUser User loginUser) {
        if (schoolAdminService.authAdmin(loginUser.getNo(), schoolNo) < 1) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.status(200).body(schoolAdminService.findBySchoolNo(schoolNo));
    }

   // @GetMapping("/test")
    public void test(Model model, int schoolNo) {
        School school = schoolAdminService.getSchool(schoolNo);
        log.debug(school.getTags().getFirst().getSchoolNo());

        List<SchoolTag> schooltags = school.getTags();
        HashMap<Integer, SchoolTag> schoolTagMap = new HashMap<>();
        for (SchoolTag schoolTag : schooltags) {
            schoolTagMap.put(schoolTag.getTagNo(), schoolTag);
        }

        System.out.println(school);
        model.addAttribute("school", school);
        model.addAttribute("schoolTagMap", schoolTagMap);
        model.addAttribute("tags", tagService.findAllTag());
        for (SchoolTag tag : school.getTags()) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + tag.getTagNo());
        }
    }

    @GetMapping("/schoolInfo")
    @ResponseBody
    public Object schoolInfo(Model model, int schoolNo,@LoginUser User loginUser) {


        School school = schoolAdminService.getSchool(schoolNo);
        log.debug(school.getTags().getFirst().getSchoolNo()); //?

        List<SchoolTag> schooltags = school.getTags();
        HashMap<Integer, SchoolTag> schoolTagMap = new HashMap<>();
        for (SchoolTag schoolTag : schooltags) {
            schoolTagMap.put(schoolTag.getTagNo(), schoolTag);
        }

        System.out.println(school);
        model.addAttribute("school", school);
        model.addAttribute("photo", school.getPhoto());
        model.addAttribute("schoolTagMap", schoolTagMap);

        for (SchoolTag tag : school.getTags()) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@" + tag.getTagNo());
        }

        return school;
    }

    @PostMapping("update")
    public String update(
            School school,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @LoginUser User loginUser,
            HttpSession session) throws Exception {


        School old = schoolAdminService.getSchool(school.getNo());
        log.debug(old);
//        if (old == null) {
//            throw new Exception("번호가 유효하지 않습니다.");
//        } else if (old.getNo() != loginUser.getNo()) {
//            throw new Exception("권한이 없습니다.");
//        }

        // 파일 업로드 및 처리
        if (file != null && !file.isEmpty()) {
            String filename = fileUpload.upload(this.bucketName, this.uploadDir, file);
            school.setPhoto(filename);
        } else {
            school.setPhoto(old.getPhoto());
        }

        // 나머지 로직 실행 및 업데이트
        schoolAdminService.update(school);
        // 업데이트 후 리다이렉트
        return "redirect:/school/admin?schoolNo=" + school.getNo();
    }


    @GetMapping("/checkDuplicateSchoolName")
    @ResponseBody
    public Map<String, Integer> checkDuplicateSchoolName(String schoolName) {
        int count = schoolService.isNameExists(schoolName);

        Map<String, Integer> response = new HashMap<>();
        response.put("cnt", count);
        return response;
    }

    @GetMapping("deleteSchool")
    @ResponseBody
    public String deleteSchool(int schoolNo,ClassDeleteDTO classDeleteDTO) throws Exception {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@삭제");

        School school = schoolAdminService.getSchoolNo(schoolNo);

        System.out.print(schoolAdminService.getSchoolNo(schoolNo) + "@@@@@@@@@@@@@@@@@@@@@@@@@");


        if (school == null) {
            throw new Exception("해당 학교를 찾을 수 없습니다.");
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@tag1");
        String filename = school.getPhoto();
        if (filename != null) {
            fileUpload.delete(this.bucketName, this.uploadDir, school.getPhoto());
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@tag2");

        int a = schoolAdminService.deleteSchool(schoolNo);
        log.debug("@@@@@@@@@@@@@" + a);
        log.debug("Redirecting to index page");
        return "redirect:index";
    }
}
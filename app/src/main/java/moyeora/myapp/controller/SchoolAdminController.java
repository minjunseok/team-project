package moyeora.myapp.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dto.school.admin.MemberUpdateRequestDTO;
import moyeora.myapp.service.SchoolAdminService;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("school/admin")
public class SchoolAdminController {
  private final SchoolAdminService schoolAdminService;


  @GetMapping("")
  public void setting() {

  }

  @GetMapping("userList")
  public ResponseEntity<List<SchoolUser>> userList(int schoolNo) {
    return ResponseEntity.status(200).body(schoolAdminService.findUserBySchoolNo(schoolNo, 2));
  }

  @GetMapping("blackList")
  public ResponseEntity<List<SchoolUser>> blackList(int schoolNo) {
    return ResponseEntity.status(200).body(schoolAdminService.findUserBySchoolNo(schoolNo, 1));
  }

  @GetMapping("submitList")
  public ResponseEntity<List<SchoolUser>> submitList(int schoolNo) {
    return ResponseEntity.status(200).body(schoolAdminService.findUserBySchoolNo(schoolNo, 5));
  }


  @PostMapping("approve")
  public ResponseEntity<?> memberApprove(@RequestBody int userNo, @RequestBody int schoolNo) {
    return ResponseEntity.status(200).build();
  }

  @PostMapping("blackAdd")
  public ResponseEntity<?> blackAdd(@RequestBody MemberUpdateRequestDTO memberUpdateRequestDTO) {
    schoolAdminService.blackUpdate(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("blackDelete")
  public ResponseEntity<?> blackDelete(@RequestBody MemberUpdateRequestDTO memberUpdateRequestDTO) {
    schoolAdminService.blackDelete(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("levelUpdate")
  public ResponseEntity<Integer> memberLevelUpdate(@RequestBody MemberUpdateRequestDTO memberUpdateRequestDTO) {
    return ResponseEntity.status(200).body(schoolAdminService.levelUpdate(memberUpdateRequestDTO));
  }

//  @GetMapping("member")
//  @ResponseBody
//  public ResponseEntity<List<SchoolUser>> memberList(int schoolNo, int levelNo) {
//    return ResponseEntity.status(200).body(schoolAdminService.findUserBySchoolNo(schoolNo, levelNo));
//  }

}

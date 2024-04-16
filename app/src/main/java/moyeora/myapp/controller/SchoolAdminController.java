package moyeora.myapp.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.dto.school.admin.SchoolOpenRangeUpdateRequestDTO;
import moyeora.myapp.service.SchoolAdminService;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
  public ResponseEntity<?> memberApprove(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    schoolAdminService.approveUpdate(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("reject")
  public ResponseEntity<?> memberReject(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    schoolAdminService.deleteMember(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("blackAdd")
  public ResponseEntity<?> blackAdd(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    schoolAdminService.blackUpdate(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("blackDelete")
  public ResponseEntity<?> blackDelete(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    memberUpdateRequestDTO.setLevelNo(2);
    schoolAdminService.blackUpdate(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("levelUpdate")
  public ResponseEntity<Integer> memberLevelUpdate(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    return ResponseEntity.status(200).body(schoolAdminService.levelUpdate(memberUpdateRequestDTO));
  }

  @PostMapping("openClosed")
  public ResponseEntity<?> openClosed(@RequestBody SchoolOpenRangeUpdateRequestDTO schoolOpenRangeUpdateRequestDTO) {
    schoolAdminService.updateSchoolOpenRange(schoolOpenRangeUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @GetMapping("openClosedCheck")
  @ResponseBody
  public ResponseEntity<School> openClosedCheck(int schoolNo) {
    return ResponseEntity.status(200).body(schoolAdminService.findBySchoolNo(schoolNo));
  }

}

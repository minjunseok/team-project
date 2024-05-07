package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.school.admin.SchoolMemberUpdateRequestDTO;
import moyeora.myapp.dto.school.admin.SchoolOpenRangeUpdateRequestDTO;
import moyeora.myapp.service.SchoolAdminService;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("school/admin")
public class SchoolAdminController {
  private final SchoolAdminService schoolAdminService;


  @GetMapping("")
  public String setting(int schoolNo, @LoginUser User loginUser) {

    if (schoolAdminService.authSubAdmin(loginUser.getNo(), schoolNo) < 1) {
      return "redirect:/index";
    }
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
    return ResponseEntity.status(200).build();
  }

  @PostMapping("sub/reject")
  public ResponseEntity<?> memberReject(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    if (schoolAdminService.authSubAdmin(1, memberUpdateRequestDTO.getSchoolNo()) < 1) {
      return ResponseEntity.status(401).build();
    }
    schoolAdminService.deleteMember(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("sub/blackAdd")
  public ResponseEntity<?> blackAdd(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    if (schoolAdminService.authSubAdmin(1, memberUpdateRequestDTO.getSchoolNo()) < 1) {
      return ResponseEntity.status(401).build();
    }
    memberUpdateRequestDTO.setLevelNo(1);
    schoolAdminService.blackUpdate(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("sub/blackDelete")
  public ResponseEntity<?> blackDelete(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    if (schoolAdminService.authSubAdmin(1, memberUpdateRequestDTO.getSchoolNo()) < 1) {
      return ResponseEntity.status(401).build();
    }
    memberUpdateRequestDTO.setLevelNo(2);
    schoolAdminService.blackUpdate(memberUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("levelUpdate")
  public ResponseEntity<Integer> memberLevelUpdate(@RequestBody SchoolMemberUpdateRequestDTO memberUpdateRequestDTO) {
    if (schoolAdminService.authAdmin(1, memberUpdateRequestDTO.getSchoolNo()) < 1) {
      return ResponseEntity.status(401).build();
    }
    return ResponseEntity.status(200).body(schoolAdminService.levelUpdate(memberUpdateRequestDTO));
  }

  @PostMapping("openClosed")
  public ResponseEntity<?> openClosed(@RequestBody SchoolOpenRangeUpdateRequestDTO schoolOpenRangeUpdateRequestDTO) {
    if (schoolAdminService.authAdmin(1, schoolOpenRangeUpdateRequestDTO.getSchoolNo()) < 1) {
      return ResponseEntity.status(401).build();
    }
    schoolAdminService.updateSchoolOpenRange(schoolOpenRangeUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @GetMapping("openClosedCheck")
  @ResponseBody
  public ResponseEntity<School> openClosedCheck(int schoolNo) {
    if (schoolAdminService.authAdmin(1, schoolNo) < 1) {
      return ResponseEntity.status(401).build();
    }
    return ResponseEntity.status(200).body(schoolAdminService.findBySchoolNo(schoolNo));
  }

}

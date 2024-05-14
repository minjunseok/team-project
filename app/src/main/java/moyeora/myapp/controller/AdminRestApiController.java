package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dto.admin.school.AdminSchoolBlackUpdateRequestDTO;
import moyeora.myapp.dto.admin.school.AdminSchoolListResponseDTO;
import moyeora.myapp.dto.admin.statistics.AdminUserGenderResponseDTO;
import moyeora.myapp.dto.admin.user.AdminBlackUpdateRequestDTO;
import moyeora.myapp.dto.admin.user.AdminRoleUpdateRequestDTO;
import moyeora.myapp.dto.admin.user.AdminUserListResponseDTO;
import moyeora.myapp.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminRestApiController {

  private final AdminService adminService;

  @GetMapping("user/list")
  @ResponseBody
  public ResponseEntity<List<AdminUserListResponseDTO>> userList(int pageSize) {
    System.out.println(adminService.findUserByPageSize(pageSize));
    return ResponseEntity.status(200).body(adminService.findUserByPageSize(pageSize));
  }

  @PostMapping("user/blackUpdate")
  @ResponseBody
  public ResponseEntity<?> blackUpdate(@RequestBody AdminBlackUpdateRequestDTO adminBlackUpdateRequestDTO) {
    System.out.println(adminBlackUpdateRequestDTO);
    adminService.updateBlackList(adminBlackUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @PostMapping("user/roleUpdate")
  @ResponseBody
  public ResponseEntity<?> roleUpdate(@RequestBody AdminRoleUpdateRequestDTO adminRoleUpdateRequestDTO) {
    adminService.roleUpdate(adminRoleUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @GetMapping("user/search")
  @ResponseBody
  public ResponseEntity<List<AdminUserListResponseDTO>> userSearch(String userInfo) {
    System.out.println("@@@@@@@" + userInfo);
    return ResponseEntity.status(200).body(adminService.userSearch(userInfo));
  }

  @GetMapping("school/list")
  @ResponseBody
  public ResponseEntity<List<AdminSchoolListResponseDTO>> schoolList(int pageSize) {
    System.out.println(adminService.findSchoolByPageSize(pageSize) + "@@@@@@@");
    return ResponseEntity.status(200).body(adminService.findSchoolByPageSize(pageSize));
  }


  @PostMapping("school/blackUpdate")
  @ResponseBody
  public ResponseEntity<?> schoolBlackUpdate(@RequestBody AdminSchoolBlackUpdateRequestDTO adminSchoolBlackUpdateRequestDTO) {
    System.out.println(adminSchoolBlackUpdateRequestDTO);
    adminService.updateSchoolBlackList(adminSchoolBlackUpdateRequestDTO);
    return ResponseEntity.status(200).build();
  }

  @GetMapping("school/search")
  @ResponseBody
  public ResponseEntity<List<AdminSchoolListResponseDTO>> schoolSearch(String schoolInfo) {
    return ResponseEntity.status(200).body(adminService.schoolSearch(schoolInfo));
  }

  @GetMapping("statistics/gender")
  @ResponseBody
  public ResponseEntity<List<AdminUserGenderResponseDTO>> statisticsGender() {
    return ResponseEntity.status(200).body(adminService.statisticsGender());
  }

  @GetMapping("statistics/age")
  @ResponseBody
  public ResponseEntity<List<?>> statisticsAge() {
    return ResponseEntity.status(200).body(adminService.statisticsBirth());
  }

  @GetMapping("statistics/hobby")
  @ResponseBody
  public ResponseEntity<List<?>> statisticsHobby() {
    return ResponseEntity.status(200).body(adminService.statisticsHobby());
  }

  @GetMapping("statistics/local")
  @ResponseBody
  public ResponseEntity<List<?>> statisticsLocal() {
    return ResponseEntity.status(200).body(adminService.statisticsLocal());
  }
}

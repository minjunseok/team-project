package moyeora.myapp.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.AdminService;
import moyeora.myapp.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminRestApiController {

  private final AdminService adminService;

  @GetMapping("user/list")
  public ResponseEntity<List<User>> userList(int pageSize) {
    return ResponseEntity.status(200).body(adminService.findByPageSize(pageSize));
  }

}

package moyeora.myapp.service;

import moyeora.myapp.dto.admin.school.AdminSchoolBlackUpdateRequestDTO;
import moyeora.myapp.dto.admin.school.AdminSchoolListResponseDTO;
import moyeora.myapp.dto.admin.user.AdminBlackUpdateRequestDTO;
import moyeora.myapp.dto.admin.user.AdminRoleUpdateRequestDTO;
import moyeora.myapp.dto.admin.user.AdminUserListResponseDTO;

import java.util.List;

public interface AdminService {

  List<AdminUserListResponseDTO> findUserByPageSize(int pageSize);

  void updateBlackList(AdminBlackUpdateRequestDTO adminBlackUpdateRequestDTO);

  void updateSchoolBlackList(AdminSchoolBlackUpdateRequestDTO adminSchoolBlackUpdateRequestDTO);

  void roleUpdate(AdminRoleUpdateRequestDTO adminRoleUpdateRequestDTO);

  List<AdminUserListResponseDTO> userSearch(String userInfo);

  List<AdminSchoolListResponseDTO> findSchoolByPageSize(int pageSize);

  List<AdminSchoolListResponseDTO> schoolSearch(String schoolInfo);
}

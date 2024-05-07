package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.dao.UserTagDao;
import moyeora.myapp.dto.admin.school.AdminSchoolBlackUpdateRequestDTO;
import moyeora.myapp.dto.admin.school.AdminSchoolListResponseDTO;
import moyeora.myapp.dto.admin.statistics.AdminUserBirthResponseDTO;
import moyeora.myapp.dto.admin.statistics.AdminUserGenderResponseDTO;
import moyeora.myapp.dto.admin.statistics.AdminUserHobbyResponseDTO;
import moyeora.myapp.dto.admin.statistics.AdminUserLocalResponseDTO;
import moyeora.myapp.dto.admin.user.AdminBlackUpdateRequestDTO;
import moyeora.myapp.dto.admin.user.AdminRoleUpdateRequestDTO;
import moyeora.myapp.dto.admin.user.AdminUserListResponseDTO;
import moyeora.myapp.service.AdminService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DefaultAdminService implements AdminService {
  private final UserDao userDao;
  private final SchoolDao schoolDao;
  private final UserTagDao userTagDao;

  public List<AdminUserListResponseDTO> findUserByPageSize(int pageSize) {
    return userDao.findAllNoMaster((pageSize - 1) * 5, pageSize * 5);
  }

  public List<AdminSchoolListResponseDTO> findSchoolByPageSize(int pageSize) {
    return schoolDao.findAllByPageSize((pageSize - 1) * 5, pageSize * 5);
  }


  public void updateBlackList(AdminBlackUpdateRequestDTO adminBlackUpdateRequestDTO) {
    List<Integer> blackList = adminBlackUpdateRequestDTO.getBlackValue();
    List<LocalDate> dates = new ArrayList<>();
    for (int i : blackList) {
      if (i == 0) {
        dates.add(null);
      }
      if (i == 1) {
        dates.add(LocalDate.now().plusWeeks(1));
      }
      if (i == 2) {
        dates.add(LocalDate.now().plusMonths(1));
      }
      if (i == 3) {
        dates.add(LocalDate.of(2999, 12, 30));
      }
    }
    for (int i = 0; i < dates.size(); i++) {
      userDao.updateBlackList(adminBlackUpdateRequestDTO.getUserNoList().get(i), dates.get(i));
    }
  }

  public void updateSchoolBlackList(AdminSchoolBlackUpdateRequestDTO adminschoolBlackUpdateRequestDTO) {
    List<Integer> blackList = adminschoolBlackUpdateRequestDTO.getBlackValue();
    List<LocalDate> dates = new ArrayList<>();
    for (int i : blackList) {
      if (i == 0) {
        dates.add(null);
      }
      if (i == 1) {
        dates.add(LocalDate.now().plusWeeks(1));
      }
      if (i == 2) {
        dates.add(LocalDate.now().plusMonths(1));
      }
      if (i == 3) {
        dates.add(LocalDate.of(2999, 12, 30));
      }
    }
    for (int i = 0; i < dates.size(); i++) {
      schoolDao.updateBlackList(adminschoolBlackUpdateRequestDTO.getSchoolNoList().get(i), dates.get(i));
    }
  }

  public void roleUpdate(AdminRoleUpdateRequestDTO adminRoleUpdateRequestDTO) {
    List<Integer> userNoList = adminRoleUpdateRequestDTO.getUserNoList();
    List<String> roleValue = adminRoleUpdateRequestDTO.getRoleValue();
    for (int i = 0; i < roleValue.size(); i++) {
      if (roleValue.get(i).equals("User")) {
        userDao.updateRole(userNoList.get(i), 1);
      }
      if (roleValue.get(i).equals("Admin")) {
        userDao.updateRole(userNoList.get(i), 0);
      }
    }
  }

  public List<AdminUserListResponseDTO> userSearch(String userInfo) {
    return userDao.findByUserInfo(userInfo);
  }

  public List<AdminSchoolListResponseDTO> schoolSearch(String schoolInfo) {
    Type listType = new TypeToken<List<AdminSchoolListResponseDTO>>() {
    }.getType();
    return new ModelMapper().map(schoolDao.findBySchoolInfo(schoolInfo), listType);
  }

  public List<AdminUserGenderResponseDTO> statisticsGender() {
    return userDao.findGroupByGender();
  }

  public List<AdminUserBirthResponseDTO> statisticsBirth() {
    return userDao.findGroupByBirth();
  }

  public List<AdminUserLocalResponseDTO> statisticsLocal() {
    return userDao.findGroupByLocal();
  }

  public List<AdminUserHobbyResponseDTO> statisticsHobby() {
    return userTagDao.findGroupByHobby();
  }
}

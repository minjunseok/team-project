package moyeora.myapp.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.AdminService;
import moyeora.myapp.vo.User;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultAdminService implements AdminService {
  private final UserDao userDao;

  public List<User> findByPageSize(int pageSize) {
    return userDao.findAllNoMaster(pageSize * 5);
  }

}

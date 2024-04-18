package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.User;

public interface AdminService {

  List<User> findByPageSize(int pageSize);

}

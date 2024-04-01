package moyeora.myapp.service;


import java.util.List;
import moyeora.myapp.vo.SchoolUser;

public interface SchoolService {

  public SchoolUser findByUserNo(int no);

  public List<String> findWeek();
}

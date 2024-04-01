package moyeora.myapp.service;


import java.util.List;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;

public interface SchoolService {

  public SchoolUser findByUserNo(int no);

  public List<String> findWeek();
}

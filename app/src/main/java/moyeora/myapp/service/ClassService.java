package moyeora.myapp.service;


import java.util.List;
import moyeora.myapp.vo.SchoolClass;

public interface ClassService {

  public List<SchoolClass> findByDate(String date);

  public List<SchoolClass> findByUserAddress(String address);
}

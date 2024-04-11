package moyeora.myapp.service;


import java.util.List;
import moyeora.myapp.vo.Class;

public interface ClassService {

  public List<Class> findByDate(String date);

  public List<Class> findByUserAddress(String address);
}

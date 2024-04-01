package moyeora.myapp.service.Impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.ClassDao;
import moyeora.myapp.service.ClassService;
import moyeora.myapp.vo.Class;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultClassService implements ClassService {

  private final ClassDao classDao;

  public List<Class> findByDate(String date) {
    System.out.println("서비스 객체 호출"+date);
    return classDao.findByDate(date);
  }
}

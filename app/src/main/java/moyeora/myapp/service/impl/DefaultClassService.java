package moyeora.myapp.service.impl;


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
    return classDao.findByDate(date);
  }
}

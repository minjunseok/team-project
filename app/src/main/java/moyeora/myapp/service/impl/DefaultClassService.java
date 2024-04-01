package moyeora.myapp.service.Impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.ClassDao;
import moyeora.myapp.vo.Class;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultClassService {

  private final ClassDao classDao;

  public List<Class> findByDate(String Date) {
    return classDao.findByDate(Date);
  }
}

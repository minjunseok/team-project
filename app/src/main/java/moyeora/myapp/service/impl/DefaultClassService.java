package moyeora.myapp.service.impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.ClassDao;
import moyeora.myapp.service.ClassService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.Class;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultClassService implements ClassService {

  private final ClassDao classDao;

  private final FileUploadHelper fileUploadHelper;
  @Override
  public List<Class> findByDate(String date) {
    return classDao.findByDate(date);
  }

  @Override
  public List<Class> findByUserAddress(String address) {
    return classDao.findByUserAddress(address);
  }
}

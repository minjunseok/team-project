package moyeora.myapp.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.vo.Class;
import org.springframework.stereotype.Service;

public interface ClassService {

  public List<Class> findByDate(String date);
}

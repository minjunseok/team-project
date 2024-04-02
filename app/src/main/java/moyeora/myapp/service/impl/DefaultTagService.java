package moyeora.myapp.service.impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.TagDao;
import moyeora.myapp.service.TagService;
import moyeora.myapp.vo.Tag;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultTagService implements TagService {
  private final TagDao tagDao;
  @Override
  public List<Tag> findAllTag() {
    return tagDao.findAll();
  }
}

package moyeora.myapp.service.impl;


import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.service.MyPageService;
import moyeora.myapp.vo.Post;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultMyPageService implements MyPageService {
  private final PostDao postDao;

  @Override
  public List<Post> findNewPost(int category) {
    return postDao.findNewPost(category);
  }
}

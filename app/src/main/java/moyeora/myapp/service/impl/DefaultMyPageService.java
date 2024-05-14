package moyeora.myapp.service.impl;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.service.MyPageService;
import moyeora.myapp.vo.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMyPageService implements MyPageService {
  private final PostDao postDao;

  @Override
  public List<Post> findNewPost(int no) {
    System.out.println("@@@@@@@@@@@@@@@@@@@"+postDao.findByUser(no));
    return postDao.findByUser(no);
  }

  @Override
  public List<Post> findHotPost(int no) {
    return postDao.findByLike();
  }

  @Override
  public List<Post> findFollowPost(int no) {
    return postDao.findByFollow(no);
  }
}

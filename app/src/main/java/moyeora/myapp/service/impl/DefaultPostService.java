package moyeora.myapp.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.service.PostService;
import moyeora.myapp.vo.Post;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DefaultPostService implements PostService {

  private final PostDao postDao;

  @Override
  public List<Post> findByLike() {
    return null;
  }

  @Override
  public List<Post> findByFollow() {
    return null;
  }

  @Override
  public List<Post> findByUser(int no) {
    return null;
  }

  @Override
  public List<Post> findBySchoolPostList(int schoolNo) {
   return postDao.findBySchoolPostList(schoolNo);
  }

  @Override
  public List<Post> findBySchoolPost() {
    return postDao.findBySchoolPost();


  }
}

package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.Post;


public interface MyPageService {
  public List<Post> findNewPost(int no);
}

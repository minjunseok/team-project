package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.Post;

public interface PostDao {

  public List<Post> findNewPost(int category);
}

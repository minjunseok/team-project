package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.Post;

public interface PostService {

  public List<Post> findByLike();

  public List<Post> findByFollow();

  public List<Post> findByUser(int no);

  public List<Post> findBySchoolPost();

  public List<Post> findBySchoolPostList(int schoolNo);
}

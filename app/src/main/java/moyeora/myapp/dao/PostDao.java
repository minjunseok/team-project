package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostDao {

  void add(Post post);

  List<Post> findAll(@Param("categoryNo") int categoryNo);

  int delete(int no);


  int update(Post post);

  int countAll(int categoryNo);

  public List<Post> findByLike();

  public List<Post> findByFollow();

  public List<Post> findByUser(int no);

  public List<Post> findByFollow(int no);

//  Post findBy(int no);

  Post findByPost(int no , int schoolNo);


  List<Post> findBySchoolPostList(int schoolNo);
}

package moyeora.myapp.dao;


import java.util.List;
import lombok.Value;
import moyeora.myapp.vo.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostDao {
  public List<Post> findByLike();

  public List<Post> findByFollow();

  public List<Post> findByUser(int no, int categoryNo);

}

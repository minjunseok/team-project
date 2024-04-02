package moyeora.myapp.dao;

import moyeora.myapp.vo.Post;
import java.util.List;

import moyeora.myapp.vo.PostCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostDao {

  void add(Post post);

  int delete(int no);

  List<Post> findAll(
      @Param("categoryNo") int categoryNo,
      @Param("offset") int offset,
      @Param("rowCount") int rowCount);

  Post findBy(int no);

  int update(Post post);

  int countAll(PostCategory category);
}

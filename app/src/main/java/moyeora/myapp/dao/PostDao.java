package moyeora.myapp.dao;

import moyeora.myapp.vo.Post;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PostDao {

  void add(Post post);

  List<Post> findAll(@Param("categoryNo") int categoryNo);

  int delete(int no);

  Post findBy(int no);

  int update(Post post);

  int countAll(int categoryNo);

  String findByPost(String content);
}

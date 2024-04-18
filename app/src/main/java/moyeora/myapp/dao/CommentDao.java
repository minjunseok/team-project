package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao {

List<Comment> findByComment(int no);

}

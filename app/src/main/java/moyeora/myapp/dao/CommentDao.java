package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.Comment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentDao {

List<Comment> findByComment(int no);

void addComment (Comment comment);

void update (Comment comment);

void delete (int commentNo);

}

package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.Comment;

public interface CommentService {

  List<Comment> get(int no);

  void addComment(Comment comment);

  void update(Comment comment);

  void delete(int commentNo);

}

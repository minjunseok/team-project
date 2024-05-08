package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.Comment;

public interface CommentService {

  List<Comment> get(int no);

  void add(Comment comment);

}

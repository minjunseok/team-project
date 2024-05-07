package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.CommentDao;
import moyeora.myapp.service.CommentService;
import moyeora.myapp.vo.Comment;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DefaultCommentService implements CommentService {

  private final CommentDao commentDao;


  @Override
  public List<Comment> get(int no) {
    return commentDao.findByComment(no);
  }
}

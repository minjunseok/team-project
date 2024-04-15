package moyeora.myapp.service;

import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Comment;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.User;

import java.sql.Date;
import java.util.List;

public interface PostService {

  void add(Post post);

  List<Post> findAll(int categoryNo);

//  Post get(int no);

  List<Comment> getComments(int no);

  int delete(int no);

  List<AttachedFile> getAttachedFiles(int no);

  AttachedFile getAttachedFile(int fileNo);

  int deleteAttachedFile(int fileNo);

 List<Post> findBySchoolPostList(int schoolNo);

 Post get(int no, int schoolNo);
}

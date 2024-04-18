package moyeora.myapp.service;

import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Comment;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.User;

import java.sql.Date;
import java.util.List;

public interface PostService {


  int update(Post post);

  void add(Post post);

  List<Post> findAll(int categoryNo);

  Post get(int no);

  List<AttachedFile> getAttachedFiles(int no);

  List<Comment> getComments(int no);

  int delete(int no);

  AttachedFile getAttachedFile(int fileNo);

  int deleteAttachedFile(int fileNo);

  int countAll(int categoryNo);

  public String findByPost(int schoolNo, String content);


 List<Post> findBySchoolPostList(int schoolNo);

 Post get(int no, int schoolNo);


  public List<Post> findByLike();

  public List<Post> findByFollow();

  public List<Post> findByUser(int no);

  public List<Post> findBySchoolPost();



// 필터 내용으로 검색했을 때
 public List<Post> findBySchoolContent(int schoolNo, String keyword);
 // 필터 작성자로 검색했을 때
 public List<Post> findBySchoolUserName(int schoolNo, String keyword);

 public List<Post> findBySchool(int schoolNo);
}

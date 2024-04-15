package moyeora.myapp.service;

import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.User;

import java.sql.Date;
import java.util.List;

public interface PostService {

//  void add(Post post);
  void add(Post post);

  List<Post> findAll(int categoryNo);


  Post get(int no);

  int update(Post post);

  int delete(int no);

  List<AttachedFile> getAttachedFiles(int no);

  AttachedFile getAttachedFile(int fileNo);

  int deleteAttachedFile(int fileNo);

  int countAll(int categoryNo);

  String findByPost(String content);

  public List<Post> findByLike();

  public List<Post> findByFollow();

  public List<Post> findByUser(int no);

  public List<Post> findBySchoolPost();

  public List<Post> findBySchoolPostList(int schoolNo);

// 필터 내용으로 검색했을 때
 public List<Post> findBySchoolContent(String keyword);
 // 필터 작성자로 검색했을 때
 public List<Post> findBySchoolUserName(String keyword);
}

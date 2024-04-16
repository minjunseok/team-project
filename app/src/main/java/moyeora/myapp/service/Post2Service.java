package moyeora.myapp.service;

import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;

import java.util.List;

public interface Post2Service {

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

  public String findByPost(int schoolNo, String content);

  public List<Post> findByLike();

  public List<Post> findByFollow();

  public List<Post> findByUser(int no);

  public List<Post> findBySchoolPost();

  public List<Post> findBySchoolPostList(int schoolNo);

// 필터 내용으로 검색했을 때
 public List<Post> findBySchoolContent(int schoolNo, String keyword);
 // 필터 작성자로 검색했을 때
 public List<Post> findBySchoolUserName(int schoolNo, String keyword);

 public List<Post> findBySchool(int schoolNo);
}

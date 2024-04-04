package moyeora.myapp.service;

import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;

import java.sql.Date;
import java.util.List;

public interface PostService {

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
}

package moyeora.myapp.service;

import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;
import moyeora.myapp.vo.PostCategory;

import java.util.List;

public interface PostService {

  void add(Post post);

  List<Post> list(PostCategory categoryNo, int pageNo, int pageSize);

  Post get(int no);

  int update(Post post);

  int delete(int no);

  List<AttachedFile> getAttachedFiles(int no);

  AttachedFile getAttachedFile(int fileNo);

  int deleteAttachedFile(int fileNo);

  int countAll(PostCategory categoryNo);
}

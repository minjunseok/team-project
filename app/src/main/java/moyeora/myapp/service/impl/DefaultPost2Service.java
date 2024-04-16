package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.AttachedFileDao;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.service.PostService;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultPost2Service implements PostService {

  private final PostDao postDao;
  private final AttachedFileDao attachedFileDao;

     // 공지글과 일반글을 구분하기 위한 리스트
    private List<Post> noticePosts = new ArrayList<>();
    private List<Post> normalPosts = new ArrayList<>();


  @Transactional
  @Override
  public void add(Post post) {
    postDao.add(post);

//    if (post.getFileList() != null && post.getFileList().size() > 0) {
//      for (AttachedFile attachedFile : post.getFileList()) {
//        attachedFile.setPostNo(post.getNo());
//      }
//      attachedFileDao.addAll(post.getFileList());
//    }
  }

  @Override
  public List<Post> findAll(int schoolNo) {

    return postDao.findAll(schoolNo);
  }


  @Override
  public Post get(int no) {
    return postDao.findBy(no);
  }

  @Transactional
  @Override
  public int update(Post post) {
    int count = postDao.update(post);
    if (post.getFileList() != null && post.getFileList().size() > 0) {
      for (AttachedFile attachedFile : post.getFileList()) {
        attachedFile.setPostNo(post.getNo());
      }
      attachedFileDao.addAll(post.getFileList());
    }
    return count;
  }

  @Transactional
  @Override
  public int delete(int no) {
//    attachedFileDao.deleteAll(no);
    return postDao.delete(no);
  }

  @Override
  public List<AttachedFile> getAttachedFiles(int no) {
    return attachedFileDao.findAllByPostNo(no);
  }

  @Override
  public AttachedFile getAttachedFile(int fileNo) {
    return attachedFileDao.findByNo(fileNo);
  }

  @Override
  public int deleteAttachedFile(int fileNo) {
    return attachedFileDao.delete(fileNo);
  }

  @Override
  public int countAll(int schoolNo) {
    return postDao.countAll(schoolNo);
  }

  @Override
  public String findByPost(int schoolNo, String content) {
    return postDao.findByPost(schoolNo, content);
  }
  @Override
  public List<Post> findByLike() {
    return null;
  }

  @Override
  public List<Post> findByFollow() {
    return null;
  }

  @Override
  public List<Post> findByUser(int no) {
    return null;
  }

  @Override
  public List<Post> findBySchoolPostList(int schoolNo) {
   return postDao.findBySchoolPostList(schoolNo);
  }

  @Override
  public List<Post> findBySchoolPost() {
    return postDao.findBySchoolPost();
  }

   // 필터를  내용으로 검색했을 때
    @Override
    public List<Post> findBySchoolContent(int schoolNo, String keyword) {
        return postDao.findBySchoolContent(schoolNo, keyword);
    }

    // 필터를 작성자로 검색했을 때
    public List<Post> findBySchoolUserName(int schoolNo, String keyword) {
        return postDao.findBySchoolUserName(schoolNo, keyword);
    }

      @Override
    public List<Post> findBySchool(int schoolNo) {
        return postDao.findBySchool(schoolNo);
    }
}

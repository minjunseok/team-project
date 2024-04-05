package moyeora.myapp.service.impl;

import moyeora.myapp.dao.AttachedFileDao;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.service.PostService;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;

import java.sql.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {

  private final PostDao postDao;
  private final AttachedFileDao attachedFileDao;

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
  public List<Post> findAll(int categoryNo) {
    return postDao.findAll(categoryNo);
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
    attachedFileDao.deleteAll(no);
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
  public int countAll(int categoryNo) {
    return postDao.countAll(categoryNo);
  }

  @Override
  public String findByPost(String content) {
    return postDao.findByPost(content);
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
}

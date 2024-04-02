package moyeora.myapp.service.impl;

import moyeora.myapp.dao.AttachedFileDao;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.service.PostService;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Post;

import java.util.List;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.vo.PostCategory;
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
    if (post.getFileList() != null && post.getFileList().size() > 0) {
      for (AttachedFile attachedFile : post.getFileList()) {
        attachedFile.setPostNo(post.getNo());
      }
      attachedFileDao.addAll(post.getFileList());
    }
  }

  @Override
  public List<Post> list(PostCategory categoryNo, int pageNo, int pageSize) {
    return postDao.findAll(categoryNo.getNo(), pageSize * (pageNo - 1), pageSize);
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
  public int countAll(PostCategory categoryNo) {
    return postDao.countAll(categoryNo);
  }
}

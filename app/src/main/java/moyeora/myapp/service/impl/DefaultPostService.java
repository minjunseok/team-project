package moyeora.myapp.service.impl;

import moyeora.myapp.dao.AttachedFileDao;
import moyeora.myapp.dao.CommentDao;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.service.PostService;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Comment;
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
  private final CommentDao commentDao;

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
//
//  @Override
//  public Post get(int no) {
//    return postDao.find(no);
//  }

  @Override
  public List<Post> findAll(int categoryNo) {
    return postDao.findAll(categoryNo);
  }





  @Transactional
  @Override
  public int delete(int no) {
    attachedFileDao.deleteAll(no);
    return postDao.delete(no);
  }

  @Override
  public List<Comment> getComments(int no) {
    return commentDao.findByComment(no);
  }

  @Override
  public List<AttachedFile> getAttachedFiles(int no) {
    return attachedFileDao.findByPostFiles(no);
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
  public List<Post> findBySchoolPostList(int schoolNo) {
   return postDao.findBySchoolPostList(schoolNo);
  }

  @Override
  public Post get(int no , int schoolNo) {
    return postDao.findByPost(no, schoolNo);
  }
}

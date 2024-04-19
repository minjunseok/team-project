package moyeora.myapp.service.impl;

import moyeora.myapp.dao.AttachedFileDao;
import moyeora.myapp.dao.CommentDao;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.service.PostService;
import moyeora.myapp.util.FileUploadHelper;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Comment;
import moyeora.myapp.vo.Post;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {

  private final PostDao postDao;
  private final AttachedFileDao attachedFileDao;
  private final CommentDao commentDao;

  // 공지글과 일반글을 구분하기 위한 리스트
  private List<Post> noticePosts = new ArrayList<>();
  private List<Post> normalPosts = new ArrayList<>();



  @Override
  public List<Post> findBySchoolPost() {
    return postDao.findBySchoolPost();
  }

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


  // String으로 데이터 이름 강제 부여
//  @Override
//  public void add(String post) {
//     postDao.add(post);
//  }

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
//    if (post.getFileList() != null && post.getFileList().size() > 0) {
//      for (AttachedFile attachedFile : post.getFileList()) {
//        attachedFile.setPostNo(post.getNo());
//      }
//      attachedFileDao.addAll(post.getFileList());
//    }
    return count;
  }

  @Transactional
  @Override
  public int delete(int no, int schoolNo) {
//    attachedFileDao.deleteAll(no);
    return postDao.delete(no, schoolNo);
  }


  //스쿨 게시글 상세조회 댓글 관련
  @Override
  public List<Comment> getComments(int no) {
    return commentDao.findByComment(no);
  }


  //스쿨 게시글 상세조회 파일목록 관련
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
  public int countAll(int schoolNo) {
    return postDao.countAll(schoolNo);
  }

  //  @Override
  public String findByPost(int schoolNo, String content) {
    return postDao.findByPost(schoolNo, content);
  }

  @Override
  public Post findByPost(int no, int schoolNo) {
    return postDao.findByPost(no, schoolNo);
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
  public Post get(int no, int schoolNo) {
    return postDao.findByPost(no, schoolNo);
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

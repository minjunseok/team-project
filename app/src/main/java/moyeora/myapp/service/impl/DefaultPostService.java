package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.AttachedFileDao;
import moyeora.myapp.dao.CommentDao;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.PostService;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Comment;
import moyeora.myapp.vo.Post;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultPostService implements PostService {

    private final PostDao postDao;
    private final AttachedFileDao attachedFileDao;
    private final CommentDao commentDao;
    private final UserDao userDao;

    @Override
    public int get(Post post) {
        return postDao.get(post);
    }


    @Override
    public int findByPostSchoolNo(int no) {
        return postDao.findByPostSchoolNo(no);
    }

    @Override
    public List<Post> findBySchoolPost() {
        return postDao.findBySchoolPost();
    }

    @Transactional
    @Override
    public void add(Post post) {
        if (post.getFileList() != null && post.getFileList().size() > 0) {
            for (AttachedFile attachedFile : post.getFileList()) {
                attachedFile.setPostNo(post.getNo());
            }
            attachedFileDao.addAll(post.getFileList());
        }
        postDao.add(post);
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
        attachedFileDao.deleteAll(no);
        // postDao.commentDelete(no);
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


    @Override
    public Post findByFixList(int schoolNo) {
        return postDao.findByFixList(schoolNo);
    }

    @Transactional
    @Override
    public void addNotice(Post post) {
        postDao.addNotice(post);
    }


    @Override
    public List<Post> findByNotice(int schoolNo) {
        return postDao.findByNotice(schoolNo);
    }


    @Override
    public int noticeFixedCancel(Post post) {
         return postDao.noticeFixedCancel(post);
    }

    @Override
  public int fixedCancel(Post post) {
    return postDao.fixedCancel(post);
  }

  @Override
  public int fixedPost(Post post) {

    return postDao.fixedPost(post);
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

    @Override
    public int getUserNo(int postNo) {
        return postDao.findByPostNo(postNo);
    }
}

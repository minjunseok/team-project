package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.CommentDao;
import moyeora.myapp.dao.PostDao;
import moyeora.myapp.dao.UserDao;

import moyeora.myapp.service.CommentService;
import moyeora.myapp.vo.Comment;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class DefaultCommentService implements CommentService {

  private final CommentDao commentDao;
  UserDao userDao;

  @Transactional
  @Override
  public void addComment(Comment comment) {

    commentDao.addComment(comment);

  }

  @Transactional
  @Override
  public void update(Comment comment) {

    commentDao.update(comment);
  }

  @Override
  public void delete(int commentNo) {
    commentDao.delete(commentNo);
  }

//  @Override
//  @Transactional
//  public void add(School school, int userNo, int schoolUserNo) {
//
//    //유저의 등급을 가져오기
//    int userGrade = userDao.findUserGrade(userNo);
//
//    //유저 넘버를 통해서 몇개를 개설했는지 SCHOOLUSER컬럼에서 4인 갯수 받아오기
//    int schoolUserLevelCount = schoolUserDao.schoolUserLevelCount(schoolUserNo);
//
//    //유저의 등급에 따라서 갯수 차별화 두기
//    //userGrade가 0일 경우 1, 1일 경우 2, 2일 경우 3이므로
//    //userGrade + 1 >= schoolUserLevelCount
//    if (userGrade + 1 >= schoolUserLevelCount) {
//      if (school.getTagNums() != null) {
//        schoolDao.add(school);
//        for (int tagNum : school.getTagNums()) {
//          schoolTagDao.add(tagNum, school.getNo());
//          System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + tagNum);
//        }
//      }
//
//      SchoolUser schoolUser = new SchoolUser();
//      schoolUser.setSchoolNo(school.getNo());
//      schoolUser.setLevelNo(4);
//      schoolUser.setUserNo(userNo);
//      schoolUserDao.insert(schoolUser);
//
//      if (school.getName() == null || school.getName().isEmpty()) {
//        throw new IllegalArgumentException("스쿨명을 입력하세요.");
//      }
//
//      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + school);
//    } else {
//      throw new IllegalStateException("유저의 grade이 낮아 스쿨을 개설할 수 없습니다.");
//    }
//  }

  @Override
  public List<Comment> get(int no) {
    return commentDao.findByComment(no);
  }
}

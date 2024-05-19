package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolDao;
import moyeora.myapp.dao.SchoolTagDao;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.dao.UserDao;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultSchoolService implements SchoolService {

    private final SchoolDao schoolDao;
    private final SchoolUserDao schoolUserDao;
    private final SchoolTagDao schoolTagDao;
    private final UserDao userDao;
    @Autowired
    private UserService userService;


    public SchoolUser findByUserNo(int no) {
        return schoolUserDao.findByUserNo(no);

    }


    @Override
    public List<String> findWeek() {
        Calendar c = Calendar.getInstance();
        //c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(dateformat.format(c.getTime()));
            c.add(Calendar.DAY_OF_YEAR, 1);
        }
        return list;
    }

    @Override
    public List<School> findHotSchool(int category) {

        return schoolDao.findHotSchool(category);
    }

    @Override
    @Transactional
    public void add(School school, int userNo, int schoolUserNo) {

        //유저의 등급을 가져오기
        int userGrade = userDao.findUserGrade(userNo);

        //유저 넘버를 통해서 몇개를 개설했는지 SCHOOLUSER컬럼에서 4인 갯수 받아오기
        int schoolUserLevelCount = schoolUserDao.schoolUserLevelCount(schoolUserNo);

        //유저의 등급에 따라서 갯수 차별화 두기
        //userGrade가 0일 경우 1, 1일 경우 2, 2일 경우 3이므로
        //userGrade + 1 >= schoolUserLevelCount
        if (userGrade + 1 >= schoolUserLevelCount) {
            if (school.getTagNums() != null) {
                schoolDao.add(school);
                for (int tagNum : school.getTagNums()) {
                    schoolTagDao.add(tagNum, school.getNo());
                    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + tagNum);
                }
            }

            SchoolUser schoolUser = new SchoolUser();
            schoolUser.setSchoolNo(school.getNo());
            schoolUser.setLevelNo(4);
            schoolUser.setUserNo(userNo);
            schoolUserDao.insert(schoolUser);

            if (school.getName() == null || school.getName().isEmpty()) {
                throw new IllegalArgumentException("스쿨명을 입력하세요.");
            }

            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + school);
        } else {
            throw new IllegalStateException("유저의 grade이 낮아 스쿨을 개설할 수 없습니다.");
        }
    }

    @Override
    public List<School> list(int categoryNo, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public School get(int schoolNo) {
        return schoolDao.findBySchool(schoolNo);
    }


    @Override
    public int delete(int postNo) {
        return 0;
    }

    @Override
    public int countAll(int categoryNo) {
        return 0;
    }

    @Override
    public List<School> findBySchoolName(String name) {
        return schoolDao.findBySchoolName(name);
    }

    @Override
    public int schoolUserLevelCount(int count) {
        return schoolUserDao.schoolUserLevelCount(count);
    } // 해당 유저가 매니저인 스쿨의 개수 카운트

    @Override
    public void insert(SchoolUser schoolUser) {
        schoolUserDao.insert(schoolUser);
    }


    public List<List<Integer>> findSchoolNoByUserNo(List<Integer> userList) {
        List<List<Integer>> arr = new ArrayList<>();
        for (int user : userList) {
            arr.add(schoolUserDao.findSchoolNoByUserNo(user));
        }
        return arr;
    }

    public void stopSchool(int schoolNo) {
        schoolDao.stopSchool(schoolNo);
    }


    @Override
    public int isNameExists(String name) {
        return schoolDao.isNameExists(name);
    }


}


package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.SchoolUserDao;
import moyeora.myapp.service.SchoolUserService;
import moyeora.myapp.vo.SchoolUser;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class DefaultSchoolUserService implements SchoolUserService {

    private final SchoolUserDao schoolUserDao;

    @Override
    public int findByUserLevelNo(int schoolNo, int userNo) {
        return schoolUserDao.findByUserLevelNo(schoolNo, userNo);
    }

    @Override
    public List<SchoolUser> findBySchoolUserList(int schoolNo) {
        return schoolUserDao.findBySchoolUserList(schoolNo);
    }


    @Override
    public void addSchoolUser(int userNo, int schoolNo, int levelNo) {
        schoolUserDao.addSchoolUser(userNo, schoolNo, levelNo);
    }


    // 스쿨에 가입이 된 유저인지 확인하기 위한 코드
    @Override
    public int joinedSchoolUser(int userNo, int schoolNo) {
        return schoolUserDao.findBySchoolNo(userNo, schoolNo);
    }
}

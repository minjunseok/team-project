package moyeora.myapp.service.impl;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.*;
import moyeora.myapp.dto.schoolclass.ClassDeleteDTO;
import moyeora.myapp.dto.schoolclass.SchoolClassRequestDTO;
import moyeora.myapp.service.SchoolClassService;
import moyeora.myapp.vo.SchoolClass;
import moyeora.myapp.vo.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultSchoolClassService implements SchoolClassService {

  private final SchoolClassDao schoolClassDao;
  private final ClassUserDao classUserDao;
  private final SchoolMemberDao schoolMemberDao;
  private final AlertDao alertDao;
  private final UserDao userDao;

  @Override
  public List<SchoolClass> findByDate(String date, int userNo) {
    User user = userDao.findByNo(userNo);
    String[] strs = user.getAddress().split(" ");
    String str = "";
    if(strs.length>2) {
      for (int i = 0; i < 2; i++) {
        str += strs[i];
        str += " ";
      }
    }
    return schoolClassDao.findByDate(date,str);
  }

  @Override
  public List<SchoolClass> findByUserAddress(String address) {
    return schoolClassDao.findByUserAddress(address);
  }

  @Override
  public void add(SchoolClass clazz) {

    schoolClassDao.add(clazz);
    System.out.println("$$$$$$$$$$$$$$$$$$$" + clazz.getSchoolNo());
    System.out.println("$$$$$$$$$$$$$$$$$$$" + clazz.getUserNo());
    System.out.println("$$$$$$$$$$$$$$$$$$$" + clazz.getNo());
    System.out.println("=================================================");

    SchoolClassRequestDTO schoolClassRequestDTO = new SchoolClassRequestDTO();
    schoolClassRequestDTO.setSchoolNo(clazz.getSchoolNo());
    schoolClassRequestDTO.setClassNo(clazz.getNo());
    schoolClassRequestDTO.setUserNo(clazz.getUserNo());

    classUserDao.insert(schoolClassRequestDTO);
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + clazz.getUserNo());
    System.out.println("$$$$$$$$$$$$$$$$$$$$444$$$" + clazz.getSchoolNo());
    System.out.println("#########################" + clazz.getNo());
  }


  @Override
  public List<SchoolClass> findBySchoolUser(int schoolNo) {
    return schoolClassDao.findBySchoolUser(schoolNo);
  }


  @Override
  public List<SchoolClass> schoolCalendarList(int schoolNo) {

    return schoolClassDao.findBySchool(schoolNo);
  }


  @Override
  public SchoolClass get(int classNo) {
    SchoolClass sc = schoolClassDao.findByNo(classNo);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println(sc);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    return sc;

  }

  @Override
  public void insert(SchoolClassRequestDTO schoolClassRequestDTO) {
    classUserDao.insert(schoolClassRequestDTO);
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println(schoolClassRequestDTO);
    System.out.println("#########################");
  }


  @Override
  public void memberDelete(SchoolClassRequestDTO schoolClassRequestDTO) {
    classUserDao.memberDelete(schoolClassRequestDTO);
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println(schoolClassRequestDTO);
    System.out.println("#########################");
  }

  @Override
  public void classDelete(ClassDeleteDTO classDeleteDTO) {
    SchoolClassRequestDTO schoolClassRequestDTO = new SchoolClassRequestDTO();


    System.out.println("@@@@@@@@@@@@");
    System.out.println(classDeleteDTO);
    System.out.println("======================");


    schoolClassDao.classDelete(classDeleteDTO);

  }

  @Override
  public int classUpdate(SchoolClass clazz) {
    return schoolClassDao.classUpdate(clazz);
  }

  @Override
  public boolean isMember(int classNo, int userNo) {
    return classUserDao.countMember(classNo, userNo) == 1;
  }

  @Override
  public List<SchoolClass> weekClass(String address, List<String> week) {
    List<String> strarr = new ArrayList<>();
    for(String date : week) {
      strarr.add("'"+date+"'");
    }
    String[] strs = address.split(" ");
    String str = "";
    if(strs.length>2) {
      for (int i = 0; i < 2; i++) {
        str += strs[i];
        str += " ";
      }
    }
    String weekString = String.join(",", strarr);
    return schoolClassDao.findByWeek(str,weekString);
  }
}

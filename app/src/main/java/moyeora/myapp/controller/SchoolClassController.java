package moyeora.myapp.controller;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.annotation.LoginUser;
import moyeora.myapp.dto.schoolclass.ClassDeleteDTO;
import moyeora.myapp.dto.schoolclass.SchoolClassRequestDTO;
import moyeora.myapp.service.SchoolClassService;
import moyeora.myapp.service.SchoolMemberService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.JsonResult;
import moyeora.myapp.vo.SchoolClass;
import moyeora.myapp.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("schoolclass")
@RequiredArgsConstructor
public class SchoolClassController {

  private static final Log log = LogFactory.getLog(SchoolClassController.class);
  private final SchoolClassService schoolClassService;
  private final FileUpload fileUpload;
  private final SchoolMemberService schoolMemberService;
  private final String uploadDir =  "schoolclass/";
  @Value("${ncp.storage.bucket}") private String bucket;


  @GetMapping("list")
  @ResponseBody
  public Object viewOfDate(String date, @LoginUser User loginUser) {
    if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
      return schoolClassService.findByDate(date, loginUser.getNo());
    } else {
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식 지정
      date = currentTime.format(formatter);
      return schoolClassService.findByDate(date, loginUser.getNo());
    }
  }

  @GetMapping("realCalendar")
  public void form(Model model,@LoginUser User loginUser) throws Exception {
//    model.addAttribute("schoolMembers",schoolMemberService.list(schoolNo));
    model.addAttribute("loginUser",loginUser.getNo());
    //    System.out.println("realCalendar@@@@@@@@" + schoolNo);

  }

  @GetMapping("calendar")
  @ResponseBody
  public List<SchoolClass> Calendar(int schoolNo) throws Exception {
    System.out.println("=====classcontorller.schoolClass==============>    " + schoolClassService);
    System.out.println("calendar@@@@@@@@" + schoolNo);
    return schoolClassService.schoolCalendarList(schoolNo);
  }




  @PostMapping("add")
  @ResponseBody
  public Object add(SchoolClass clazz, MultipartFile file,
                  LocalDateTime startAt2,
                  LocalDateTime endedAt2,
                  ZoneId zoneId,
                  @LoginUser User loginUser,
                    int schoolNo
  ) throws Exception{

    System.out.println("1111111111111111111111111111111111");
    clazz.setUserNo(loginUser.getNo());
    clazz.setSchoolNo(schoolNo);
    System.out.println("@@@@@@@@@@(add)loginUser@@@@@@@@@"+loginUser);
    System.out.println("@@@@@@@@@@(add)schoolNo@@@@@@@@@"+schoolNo);
    System.out.println("@@@@@@@@@@(add)loginUser.getNo()@@@@@@@@@"+loginUser.getNo());
    System.out.println("@@@@@@@@@@(add)file.getSize()@@@@@@@@@"+file.getSize());
    if(file.getSize() > 0){
      String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
      clazz.setPhoto(filename);
    }

    System.out.println("=====classcontorller.zonid==============>    " + zoneId);
    System.out.println("===================>    " + Date.from(startAt2.atZone(zoneId).toInstant()));

    Date startAtDate = Date.from(startAt2.atZone(zoneId).toInstant());
    Date endedAtDate = Date.from(endedAt2.atZone(zoneId).toInstant());

    clazz.setStartAt(startAtDate);
    clazz.setEndedAt(endedAtDate);
    schoolClassService.add(clazz);

    System.out.println("=======classcontroller.startdate============>    " + startAtDate);
    System.out.println("=========classcontrollr.endeddate==========>    " + endedAtDate);

    System.out.println("=======classcontroller============>    " + clazz);


    JsonResult jsonResult = new JsonResult();
    jsonResult.setStatus("success");

    return jsonResult;

  }

  @GetMapping("findByNo")
  @ResponseBody
  public Object findByNo(int classNo, @LoginUser User loginUser, Model model) throws Exception {
    HashMap<String, Object> result = new HashMap<>();
    result.put("schoolClass", schoolClassService.get(classNo));
    result.put("isMember", schoolClassService.isMember(classNo, loginUser.getNo()));

    return result;
  }

  @GetMapping("findByClassMember")
  @ResponseBody
  public Object findByClassMember(int classNo) throws Exception {
    System.out.println("@@@@@@@@@findByClassMember 실행@@@@@@@@@@@@@@@@@");
    HashMap<String, Object> result = new HashMap<>();
    System.out.println("@@@@@@@@@findByClassMember@@@@@@@@@@@@@@@@@");
    result.put("schoolMembers",schoolMemberService.list(classNo));
    System.out.println("@@@@@@@@@1111111111@@@@@@@@@@@@@@@@@"+schoolMemberService.list(classNo));
    return result;
  }

  @PostMapping("insert")
  @ResponseBody
  public Object attend(@RequestBody SchoolClassRequestDTO schoolClassRequestDTO, @LoginUser User loginUser) throws Exception {
    schoolClassRequestDTO.setUserNo(loginUser.getNo());
    System.out.println("@@@@@@@@@@(insert)loginUser.getNo()@@@@@@@@@"+loginUser.getNo());
    schoolClassService.insert(schoolClassRequestDTO);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println(schoolClassRequestDTO);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
    return "";
  }


  @PostMapping("memberDelete")
  @ResponseBody
  public Object MemberDelete(@RequestBody SchoolClassRequestDTO schoolClassRequestDTO, @LoginUser User loginUser) throws Exception {
    schoolClassRequestDTO.setUserNo(loginUser.getNo());
    System.out.println("@@@@@@@@@@(memberDelete)loginUser.getNo()@@@@@@@@@"+loginUser.getNo());
    schoolClassService.memberDelete(schoolClassRequestDTO);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println(schoolClassRequestDTO);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@");
    return "";
  }

  @PostMapping("classDelete")
  @ResponseBody
  public void classDelete(@RequestBody SchoolClass clazz, @LoginUser User loginUser) throws Exception {

    System.out.println("@@@@@@classDelete clazz.getUserNo()@@@@@@@" + clazz.getUserNo());
    System.out.println("@@@@@@classDelete loginUser.getNo()@@@@@@@" + loginUser.getNo());

  if (clazz.getUserNo() == loginUser.getNo()) {


    ClassDeleteDTO classDeleteDTO = new ClassDeleteDTO();
    classDeleteDTO.setClassNo(clazz.getNo());
    System.out.println("@@@@@@classDelete@@@@@@@" + clazz.getNo());

    classDeleteDTO.setUserNo(loginUser.getNo());
    System.out.println("@@@@classDelete@@@@@@(classDelete)loginUser.getNo()@@@@@@@@@" + loginUser.getNo());

    SchoolClassRequestDTO schoolClassRequestDTO = new SchoolClassRequestDTO();
    schoolClassRequestDTO.setUserNo(loginUser.getNo());
    schoolClassRequestDTO.setSchoolNo(clazz.getSchoolNo());
    schoolClassRequestDTO.setClassNo(classDeleteDTO.getClassNo());
    System.out.println("@@@@@@classDelete@@@@@@@");
    System.out.println(schoolClassRequestDTO);
    System.out.println("@@@@@@@classDelete@@@@@@");

    schoolClassService.memberDelete(schoolClassRequestDTO);
    schoolClassService.classDelete(classDeleteDTO);
  } else {
    throw new Exception("권한이 없습니다.");
  }

  }


    @PostMapping("classUpdate")
    @ResponseBody
    public void update(SchoolClass clazz,
                       MultipartFile file,
                       @LoginUser User loginUser,
                       LocalDateTime startAt3,
                       LocalDateTime endedAt3,
                       ZoneId zoneId) throws Exception {

      System.out.println("$$$$$$$$$$$$$classUpdate clazz.getNo$$$$$$$$$$$$$$" + clazz.getNo());
      System.out.println("=======classcontroller.startdate============>    " + startAt3);
      System.out.println("=========classcontrollr.endeddate==========>    " + endedAt3);


      SchoolClass old = schoolClassService.get(clazz.getNo());
      System.out.println("$$$$$$$$$$$$$classUpdate$ old$$$$$$$$$$$$$" + old);
      System.out.println("$$$$$$$$$$$$$$classUpdate old.getNo$$$$$$$$$$$$$" + old.getNo());
      if (old == null) {
        throw new Exception("회원 번호가 유효하지 않습니다.");
      }
      clazz.setNo(old.getNo());

      clazz.setCreatedAt(old.getCreatedAt());

      if (file != null) {
        String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
        clazz.setPhoto(filename);
        fileUpload.delete(this.bucket, this.uploadDir, old.getPhoto());
      } else {
        clazz.setPhoto(old.getPhoto());
      }

      Date startAtDate = Date.from(startAt3.atZone(zoneId).toInstant());
      Date endedAtDate = Date.from(endedAt3.atZone(zoneId).toInstant());

      clazz.setStartAt(startAtDate);
      clazz.setEndedAt(endedAtDate);

      System.out.println("=======classcontroller.startdate============>    " + startAtDate);
      System.out.println("=========classcontrollr.endeddate==========>    " + endedAtDate);

      System.out.println("$$$$$$$$$$$$$classUpdate clazz$$$$$$$$$$$$$$" + clazz);

      schoolClassService.classUpdate(clazz);
      System.out.println("@@@@업데이트 업데이트 업데이트@@@@@");
      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ clazz);
      System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+ schoolClassService);

    }

}

package moyeora.myapp.controller;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.SchoolClassService;
import moyeora.myapp.service.SchoolMemberService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.JsonResult;
import moyeora.myapp.vo.SchoolClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/schoolclass")
@RequiredArgsConstructor
public class SchoolClassController {

  private static final Log log = LogFactory.getLog(SchoolClassController.class);
  private final SchoolClassService schoolClassService;
  private final FileUpload fileUpload;
  private final SchoolMemberService schoolMemberService;
  private final String uploadDir =  "schoolclass/";
  @Value("${ncp.storage.bucket}") private String bucket;

  @GetMapping("/list")
  @ResponseBody
  public List<SchoolClass> viewOfDate(String date) {
    if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
      System.out.println("1");
      return schoolClassService.findByDate(date);
    } else {
      System.out.println("2");
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식 지정
      date = currentTime.format(formatter);
      return schoolClassService.findByDate(date);
    }
  }

  @GetMapping("realCalendar")
  public void form(Model model, int schoolNo) throws Exception{
    model.addAttribute("schoolMembers",schoolMemberService.list(schoolNo));
    System.out.println("=====classcontorller.schoolMember==============>    " + schoolMemberService);

  }

  @GetMapping("calendar")
  @ResponseBody
  public List<SchoolClass> Calendar(int schoolNo) throws Exception {
    System.out.println("=====classcontorller.schoolClass==============>    " + schoolClassService);
    return schoolClassService.schoolCalendarList(schoolNo);
  }




  @PostMapping("add")
  @ResponseBody
  public Object add(SchoolClass clazz, MultipartFile file,
                  LocalDateTime startAt2,
                  LocalDateTime endedAt2,
                  ZoneId zoneId
  ) throws Exception{
    clazz.setUserNo(2);
    if(file.getSize() > 0){
      String filename = fileUpload.upload(this.bucket, this.uploadDir, file);
      clazz.setPhoto(filename);
    }

    System.out.println("=====classcontorller.zonid==============>    " + zoneId);
    System.out.println("===================>    " + Date.from(startAt2.atZone(zoneId).toInstant()));

    Date startAtDate = Date.from(startAt2.atZone(zoneId).toInstant());
    Date endedAtDate = Date.from(endedAt2.atZone(zoneId).toInstant());

    System.out.println("=======classcontroller.startdate============>    " + startAtDate);
    System.out.println("=========classcontrollr.endeddate==========>    " + endedAtDate);


    clazz.setStartAt(startAtDate);
    clazz.setEndedAt(endedAtDate);

    schoolClassService.add(clazz);
    System.out.println("=======classcontroller============>    " + clazz);

    JsonResult jsonResult = new JsonResult();
    jsonResult.setStatus("success");

    return jsonResult;

  }




}

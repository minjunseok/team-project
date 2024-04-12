package moyeora.myapp.controller;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.ClassService;
import moyeora.myapp.service.SchoolMemberService;
import moyeora.myapp.util.FileUpload;
import moyeora.myapp.vo.Class;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/class")
@RequiredArgsConstructor
public class ClassController {

  private static final Log log = LogFactory.getLog(ClassController.class);
  private final ClassService classService;
  private final FileUpload fileUpload;
  private final SchoolMemberService schoolMemberService;
  private final String uploadDir =  "class/";
  @Value("${ncp.storage.bucket}") private String bucket;

  @GetMapping("/list")
  @ResponseBody
  public List<Class> viewOfDate(String date) {
    if (date.matches("\\d{4}-\\d{2}-\\d{2}")) {
      System.out.println("1");
      return classService.findByDate(date);
    } else {
      System.out.println("2");
      LocalDateTime currentTime = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식 지정
      date = currentTime.format(formatter);
      return classService.findByDate(date);
    }
  }

  @GetMapping("form")
  public void form(Model model,int schoolNo) throws Exception{
    model.addAttribute("schoolMembers",schoolMemberService.list(schoolNo));
    System.out.println("=====classcontorller.schoolMember==============>    " + schoolMemberService);
  }

  @PostMapping("add")
  public void add(Class clazz, MultipartFile file,
                    LocalDateTime startAt2,
                    LocalDateTime endedAt2,
                    ZoneId zoneId
  ) throws Exception{
    clazz.setUserNo(1);
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

    classService.add(clazz);
    System.out.println("=======classcontroller============>    " + clazz);
  }




}

package moyeora.myapp.vo;


import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchoolClass {
  private int no;
  private int schoolNo;
  private int userNo;
  private String name;
  private String content;
  private String location;
  private Date startAt;
  private Date createdAt;
  private Date endedAt;
  private String member;
  private String repeatSet;
  private String photo;
  private int nowMemberCount;
  private String locationDetail;


}

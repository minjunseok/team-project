package moyeora.myapp.vo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
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

  private Level level;
  private User user;
  private ClassUser classUser;
}

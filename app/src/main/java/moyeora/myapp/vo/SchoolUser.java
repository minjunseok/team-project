package moyeora.myapp.vo;

import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SchoolUser {

  private List<SchoolUser> schoolUserList;
  private int userNo;
  private int schoolNo;
  private int gradeNo;
  private Date createdAt;
  private List<School> schools;
  private String photo;
  private String gradeName;
  private String nickname;


}

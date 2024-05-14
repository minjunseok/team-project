package moyeora.myapp.vo;

import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class SchoolUser {

  private int userNo;
  private int schoolNo;
  private int levelNo;
  private String levelName;
  private Date createdAt;
  private List<School> schools;
  private List<SchoolUser> schoolUsers;
  private User writer;
  private Level level;
  private User member;
}



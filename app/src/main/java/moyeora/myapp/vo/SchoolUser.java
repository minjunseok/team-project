package moyeora.myapp.vo;

import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class SchoolUser {

  private int userNo;
  private int schoolNo;
  private int levelNo;
  private Date createdAt;
  private List<School> schools;
  private User member;
  private Level level;
}

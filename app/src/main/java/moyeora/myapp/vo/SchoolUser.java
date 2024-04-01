package moyeora.myapp.vo;

import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SchoolUser {

  private int userNo;
  private int schoolNo;
  private int gradeNo;
  private Date createdAt;
  private List<School> schools;


}

package moyeora.myapp.vo;

import java.sql.Date;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class School implements Serializable {
  private static final long  serialVersionUID = 100L;
  private int no;
  private  int categoryNo;
  private  int postNo;
  private String name;
  private String content;
  private int limitedMan;
  private String photo;
  private int openRange;
  private Date createdAt;
  private int nowMemberCount;
  private List<Integer> tagNums;
  private List<SchoolTag> tags;
  private int stop;
}

package moyeora.myapp.vo;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class School implements Serializable {
  private static final long  serialVersionUID = 100L;
  private int no;
  private int schoolNo;
  private  int categoryNo;
  private  int postNo;
  private String name;
  private String content;
  private int limitedMan;
  private String photo;
  private int openRange;
  private Date createdAt;

}

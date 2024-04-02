package moyeora.myapp.vo;


import java.sql.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
@Getter
@Setter
public class School {
  private int no;
  private String name;
  private String content;
  private int limitedMan;
  private String photo;
  private int openRange;
  private Date createdAt;

}

package moyeora.myapp.vo;


import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
  private int no;
  private String content;
  private Date createdAt;
  private int userNo;
  private int postNo;

}

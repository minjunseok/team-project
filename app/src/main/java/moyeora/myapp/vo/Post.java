package moyeora.myapp.vo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Post {
  private int no;
  private int schoolNo;
  private int userNo;
  private int categoryNo;
  private String title;
  private String content;
  private Date createdAt;

}

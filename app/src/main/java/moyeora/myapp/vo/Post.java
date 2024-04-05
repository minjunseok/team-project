package moyeora.myapp.vo;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class Post {
  private int no;
  private int schoolNo;
  private int userNo;
  private int categoryNo;
  private String content;
  private Date createdAt;
  private int likeCount;
  private int commentCount;
  private User Writer;
  private List<AttachedFile> FileList;
  private School School;
}

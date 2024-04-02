package moyeora.myapp.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Post {
  private int no;
  private int schoolNo;
  private User userNo;
  private PostCategory categoryNo;
  private List<AttachedFile> fileList;
  private String title;
  private String content;
  private Date createdAt;

}

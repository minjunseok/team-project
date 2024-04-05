package moyeora.myapp.vo;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import lombok.Data;

@Data
public class Post {
  private int no;
  private String name;
  private String photo;
  private int schoolNo;
  private int userNo;
  private int categoryNo;
  private String content;
  private Date createdAt;
  private int likeCount;
  private int commentCount;
  private User writer;
  private List<AttachedFile> fileList;
  private School school;
  private SchoolUser schoolUser;
  private List<Comment> comment;
}

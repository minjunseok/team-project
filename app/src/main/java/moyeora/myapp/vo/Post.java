package moyeora.myapp.vo;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import lombok.Data;


@Data
public class Post {

  private Post post;
  private int no;
  private String type;
  private String fileName;
  private User nickname;
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
  private int fileCount;
  private List<Comment> comments;
  private List<SchoolUser> schoolUsers;
  private List<Post> posts;
  private School school;
  private SchoolUser schoolUser;
  private String lNo;
  private String keyword;

}

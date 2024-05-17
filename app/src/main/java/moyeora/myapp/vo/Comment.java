package moyeora.myapp.vo;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Comment {
  private Post post;
  private SchoolUser schoolUser;
  private School school;
  private User writer;
  private int commentNo;
  private String content;
  private Date createdAt;
  private int userNo;
  private int postNo;
  private int schoolNo;


}

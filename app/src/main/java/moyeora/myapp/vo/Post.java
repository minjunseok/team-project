package moyeora.myapp.vo;

import java.util.Date;
import java.util.List;
<<<<<<< HEAD
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
=======
>>>>>>> 36279d08100cc172f09ed80ecfd45e8342e6d9fd

import lombok.Data;

@Data
public class Post {
  private int no;
  private int schoolNo;
  private User userNo;
  private User User;
  private int categoryNo;
<<<<<<< HEAD
=======
  private List<AttachedFile> fileList;
>>>>>>> 36279d08100cc172f09ed80ecfd45e8342e6d9fd
  private String content;
  private Date createdAt;
  private int likeCount;
  private int commentCount;
  private User Writer;
  private List<AttachedFile> FileList;
  private School School;
}

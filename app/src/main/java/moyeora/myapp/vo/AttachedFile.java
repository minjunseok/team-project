package moyeora.myapp.vo;

import java.util.Date;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AttachedFile {
  private int no;
  private int postNo;
  private String fileName;
  private String path;
  private String size;
  private Date createdAt;
  private String type;
  private String filePath;
  private Post post;
  private User writer;
  private int schoolNo;


}


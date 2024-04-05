package moyeora.myapp.vo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AttachedFile {
  private int no;
  private int postNo;
  private String name;
  private String path;
  private String size;
  private Date createdAt;
  private String type;

}

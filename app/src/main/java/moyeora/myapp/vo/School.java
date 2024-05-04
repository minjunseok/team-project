package moyeora.myapp.vo;

<<<<<<< HEAD
import lombok.Data;
=======
import lombok.Getter;
import lombok.Setter;
>>>>>>> c34590eb90791cc5691a4f645525ecdcf6e61809

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class School implements Serializable {
  private static final long  serialVersionUID = 100L;
  private int no;
  private  int categoryNo;
  private  int postNo;
  private String name;
  private String content;
  private int limitedMan;
  private String photo;
  private int openRange;
  private Date createdAt;
  private int nowMemberCount;
  private List<Integer> tagNums;
  private List<SchoolTag> tags;
  private int stop;
  private List<School> findAll;
}

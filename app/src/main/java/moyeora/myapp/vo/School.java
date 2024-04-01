package moyeora.myapp.vo;

import java.io.Serializable;
import java.sql.Date;

public class School implements Serializable {

  private static final long  serialVersionUID = 100L;

  private int schoolNo;
  private  int categoryNo;
  private  int postNo;
  private String name;
  private String content;
  private int limitedMan;
  private String photo;
  private int openRange;
  private Date createdAt;

}

package moyeora.myapp.vo;

import java.io.Serializable;
import java.sql.Date;

public class School implements Serializable {

  private static final long  serialVersionUID = 100L;

  private int school_no;
  private int post_no;
  private String name;
  private String content;
  private int limited_man;
  private String photo;
  private int open_range;
  private Date created_at;

}

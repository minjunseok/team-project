package moyeora.myapp.vo;


import lombok.Data;
import java.io.Serializable;
import java.util.Date;
@Data
public class Class implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no;
  private int schoolNo;
  private int userNo;
  private String name;
  private String security;
  private String content;
  private String location;
  private Date startAt;
  private Date createdAt;
  private Date endedAt;
  private String member;
  private String repeatSet;
  private String photo;
  private int nowMemberCount;
  private String locationDetail;


}

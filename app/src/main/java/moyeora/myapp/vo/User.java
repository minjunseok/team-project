package moyeora.myapp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

  private static final long serialVersionUID = 100L;

  private int userNo;
  private String name;
  private String pwd;
  private String address;
  private String phone;
  private String email;
  private String nickName;
  private String birth;
  private String gender;
  private String createdAt;
  private String proFile;
  private String photo;

}

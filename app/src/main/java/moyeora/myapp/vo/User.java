package moyeora.myapp.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
public class User implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no;
  private String name;
  private String password;
  private String address;
  private String phone;
  private String email;
  private String nickname;
  private String birth;
  private int gender;
  private int grade;
  private int loginType;
  private Date createdAt;
  private String profile;
  private int mannerPoint;
  private int tagNo;
  private List<Integer> tagNums;
  private List<UserTag> tags;
  private String photo;
  private String provider;
  private String providerId;
  private String role;
  private String addressDetail;
  private Date stopDate;
  private int auth;


  @Builder
  public User(String email, String name, String provider, String providerId, String role) {
    this.email = email;
    this.name = name;
    this.provider = provider;
    this.providerId = providerId;
    this.role = role;
  }

}


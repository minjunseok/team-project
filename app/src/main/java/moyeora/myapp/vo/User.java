package moyeora.myapp.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
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
  private String photo;
  private String provider;
  private String providerId;

  @Builder
  public User(String name, String phone, String email, String provider, String providerId,
      Date createdAt) {
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.provider = provider;
    this.providerId = providerId;
    this.createdAt = createdAt;
  }
}
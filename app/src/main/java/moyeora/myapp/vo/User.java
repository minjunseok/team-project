package moyeora.myapp.vo;

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
    private String brith;
    private int gender;
    private int grade;
    private int login_type;
    private Date created_at;
    private String profile;
    private int manner_point;
    private String photo;

}

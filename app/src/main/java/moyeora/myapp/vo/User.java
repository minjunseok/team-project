package moyeora.myapp.vo;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 100L;

    private int no;
    private String name;
    private String pwd;
    private String address;
    private String phone;
    private String email;
    private String nickname;
    private Date birth;
    private int gender;
    private int grade;
    private int loginType;
    private Date createdAt;
    private String profile;
    private int mannerPoint;
    private String photo;
    private List<Integer> tagNums;
    private List<UserTag> tags;
    private String addressDetail;
    private Date stopDate;
}

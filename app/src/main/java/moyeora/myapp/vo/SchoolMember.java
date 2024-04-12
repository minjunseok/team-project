package moyeora.myapp.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SchoolMember extends User {
    private Grade level;
    private Date joinedDate;
    private School school;
}

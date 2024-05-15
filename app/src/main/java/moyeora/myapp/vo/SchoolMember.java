package moyeora.myapp.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SchoolMember extends User {
    private Level level;
    private Date joinedDate;
    private SchoolClass schoolClass;
    private School school;
}

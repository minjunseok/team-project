package moyeora.myapp.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
public class SchoolMember extends User {
    private Level level;
    private Date joinedDate;
    private School school;
}

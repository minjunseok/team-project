package moyeora.myapp.dto.admin.school;


import lombok.Data;

import java.sql.Date;

@Data
public class AdminSchoolListResponseDTO {
    private int no;
    private String name;
    private String photo;
    private int nowMan;
    private int limitedMan;
    private Date createdAt;
    private Date limitedAt;
}

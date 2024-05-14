package moyeora.myapp.dto.school.admin;


import lombok.Data;

@Data
public class SchoolMemberUpdateRequestDTO {
  private int userNo;
  private int schoolNo;
  private int levelNo;

}

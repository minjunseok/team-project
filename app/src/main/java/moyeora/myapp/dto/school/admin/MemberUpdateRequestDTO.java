package moyeora.myapp.dto.school.admin;


import lombok.Data;

@Data
public class MemberUpdateRequestDTO {
  private int userNo;
  private int schoolNo;
  private int levelNo;

}

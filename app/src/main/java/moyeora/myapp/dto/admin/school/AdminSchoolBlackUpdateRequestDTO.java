package moyeora.myapp.dto.admin.school;

import lombok.Data;

import java.util.List;

@Data
public class AdminSchoolBlackUpdateRequestDTO {
    List<Integer> schoolNoList;
    List<Integer> blackValue;
}

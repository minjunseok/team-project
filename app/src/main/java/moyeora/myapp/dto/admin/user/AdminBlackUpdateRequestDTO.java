package moyeora.myapp.dto.admin.user;

import lombok.Data;

import java.util.List;


@Data
public class AdminBlackUpdateRequestDTO {
    List<Integer> userNoList;
    List<Integer> blackValue;
}

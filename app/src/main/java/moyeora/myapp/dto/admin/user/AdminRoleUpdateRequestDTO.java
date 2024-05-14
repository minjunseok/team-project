package moyeora.myapp.dto.admin.user;

import lombok.Data;

import java.util.List;

@Data
public class AdminRoleUpdateRequestDTO {
    List<Integer> userNoList;
    List<String> roleValue;
}

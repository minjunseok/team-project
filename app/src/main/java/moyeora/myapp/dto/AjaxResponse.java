package moyeora.myapp.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AjaxResponse {
    String status;
    Object data;
    String message;
}

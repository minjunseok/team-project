package moyeora.myapp.vo;

import lombok.Builder;
import lombok.Data;

@Data
public class JsonResult {
    private String status;
    private Object data;
}

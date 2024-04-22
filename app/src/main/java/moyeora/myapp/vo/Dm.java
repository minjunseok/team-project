package moyeora.myapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class Dm {
    private int no;
    private int sender;
    private int receiver;
    private String message;
    private String photo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;
}

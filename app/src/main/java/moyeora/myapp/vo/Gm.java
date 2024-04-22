package moyeora.myapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
public class Gm {
    private int no;
    private int schoolNo;
    private int sender;
    private String message;
    private String photo;
    private Date sendDate;

    @Builder
    public Gm(int schoolNo, int sender, String message, String photo, @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Date sendDate) {
        this.schoolNo = schoolNo;
        this.sender = sender;
        this.message = message;
        this.photo = photo;
        this.sendDate = sendDate;
    }
}

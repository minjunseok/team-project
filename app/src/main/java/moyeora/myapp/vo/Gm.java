package moyeora.myapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
public class Gm implements Serializable {

    private static final long serialVersionUID = 100L;

    private int no;
    private int schoolNo;
    private User sender;
    private String message;
    private String photo;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp sendDate;

    @Builder
    public Gm() {
    }

    @Builder
    public Gm(int no, int schoolNo, User sender, String message, String photo, @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp sendDate) {
        this.no = no;
        this.schoolNo = schoolNo;
        this.sender = sender;
        this.message = message;
        this.photo = photo;
        this.sendDate = sendDate;
    }

    @Builder
    public Gm(int schoolNo, User sender, String message, String photo, @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp sendDate) {
        this.schoolNo = schoolNo;
        this.sender = sender;
        this.message = message;
        this.photo = photo;
        this.sendDate = sendDate;
    }
}

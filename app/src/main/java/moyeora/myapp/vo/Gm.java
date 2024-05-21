package moyeora.myapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Gm implements Serializable {

    private static final long serialVersionUID = 100L;

    private int no;
    private School school;
    private User sender;
    private String message;
    private String photo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp sendDate;
    private String filePath;
    private int isRead;

    @Builder
    public Gm() {
    }

    @Builder
    public Gm(int no, School school, User sender, String message, String photo) {
        this.no = no;
        this.school = school;
        this.sender = sender;
        this.message = message;
        this.photo = photo;
    }

    @Builder
    public Gm(int no, School school, User sender, String message, String photo, @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") Timestamp sendDate) {
        this.no = no;
        this.school = school;
        this.sender = sender;
        this.message = message;
        this.photo = photo;
        this.sendDate = sendDate;
    }

    @Builder
    public Gm(School school, User sender, String message, String photo, @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") Timestamp sendDate) {
        this.school = school;
        this.sender = sender;
        this.message = message;
        this.photo = photo;
        this.sendDate = sendDate;
    }

    @Builder
    public Gm(School school, User sender, String message, String photo) {
        this.school = school;
        this.sender = sender;
        this.message = message;
        this.photo = photo;
    }
}

package moyeora.myapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Dm implements Serializable {

    private static final long serialVersionUID = 100L;

    private int no;
    private User sender;
    private User receiver;
    private String message;
    private String photo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Timestamp sendDate;
    private int roomNo;
    private String filePath;
    private int isRead;
    private int userNoDm;

    @Builder
    public Dm() {
    }

    @Builder
    public Dm(int no, User sender, User receiver, String message, String photo, int roomNo) {
        this.no = no;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.photo = photo;
        this.roomNo = roomNo;
    }

    @Builder
    public Dm(int no, User sender, User receiver, String message, String photo, @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") Timestamp sendDate, int roomNo) {
        this.no = no;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.photo = photo;
        this.sendDate = sendDate;
        this.roomNo = roomNo;
    }

    @Builder
    public Dm(User sender, User receiver, String message, String photo, @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")Timestamp sendDate, int roomNo) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.photo = photo;
        this.sendDate = sendDate;
        this.roomNo = roomNo;
    }

    @Builder
    public Dm(User sender, User receiver, String message, String photo, int roomNo) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.photo = photo;
        this.roomNo = roomNo;
    }

}
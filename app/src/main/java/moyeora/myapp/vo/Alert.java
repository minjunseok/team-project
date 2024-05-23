package moyeora.myapp.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Alert {
    private int alertNo;
    private int userNo;
    private String name;
    private int type;
    private String content;
    private Date createdAt;
    private int isRead;
    private String photo;
    private String redirectPath;
    private int toUserNo;
    private int schoolNo;
    private String filePath;
}

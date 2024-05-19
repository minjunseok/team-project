package moyeora.myapp.vo;

import lombok.Data;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Data
public class School implements Serializable {
    private static final long serialVersionUID = 100L;
    private int no;
    private int categoryNo;
    private int postNo;
    private String name;
    private String content;
    private int limitedMan;
    private int nowMan;
    private String photo;
    private int openRange;
    private Date createdAt;
    private Date limitedAt;
    private int nowMemberCount;
    private List<Integer> tagNums;
    private List<SchoolTag> tags;
    private int stop;
    private List<School> findAll;
}

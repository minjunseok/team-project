package moyeora.myapp.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTag {

    private int userNo;
    private int tagNo;
    private Tag tag;
}

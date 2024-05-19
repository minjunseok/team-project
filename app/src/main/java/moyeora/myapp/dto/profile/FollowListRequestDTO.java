package moyeora.myapp.dto.profile;

import lombok.Data;

@Data
public class FollowListRequestDTO {
    private int userNo;
    private int clickUserNo;
    private int type;
}

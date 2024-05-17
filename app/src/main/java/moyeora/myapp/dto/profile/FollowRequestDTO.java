package moyeora.myapp.dto.profile;

import lombok.Data;

@Data
public class FollowRequestDTO {
    private int followingUserNo;
    private int followerUserNo;
}

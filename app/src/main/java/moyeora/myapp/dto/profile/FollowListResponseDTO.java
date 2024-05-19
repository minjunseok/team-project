package moyeora.myapp.dto.profile;

import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Data
public class FollowListResponseDTO {
    private int userNo;
    private String photo;
    private String name;
    private String nickname;
    private int state = 0;
    private int followerCount;
}

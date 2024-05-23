package moyeora.myapp.dto.profile;


import lombok.Data;
import moyeora.myapp.vo.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProfileResponse2DTO {
    private List<ProfilePostResponse2DTO> posts;
}

@Data
class ProfilePostResponse2DTO {
    private int userNo;
    private String photo;
    private String nickname;
    private String email;
    private String name;
    private int postNo;
    private String content;
    private LocalDateTime createdAt;
    private int likeCount;
    private int commentCount;
    private List<Comment> comments;
}

@Data
class ProfileCommentResponse2DTO {
    private String commentNo;
    private String commentContent;
    private String commentCreatedAt;
    private ProfileCommentUserResponse2DTO commentUser;
}

@Data
class ProfileCommentUserResponse2DTO {
    private int commentUserNo;
    private String commentUserEmail;
    private String commentUserNickname;
    private String commentUserPhoto;
}
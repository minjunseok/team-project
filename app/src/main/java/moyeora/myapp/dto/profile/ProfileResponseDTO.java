package moyeora.myapp.dto.profile;


import lombok.Data;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProfileResponseDTO {
    private int userNo;
    private String photo;
    private String nickname;
    private String email;
    private String name;
    private int grade;
    private int postCount;
    private int followerCount;
    private int followingCount;
    private List<ProfilePostResponseDTO> posts;
}

@Data
class ProfilePostResponseDTO {
    private int postNo;
    private String content;
    private LocalDateTime createdAt;
    private int likeCount;
    private int commentCount;
    private List<Comment> comments;
}

@Data
class ProfileCommentResponseDTO {
    private String commentNo;
    private String commentContent;
    private String commentCreatedAt;
    private ProfileCommentUserResponseDTO commentUser;
}

@Data
class ProfileCommentUserResponseDTO {
    private int commentUserNo;
    private String commentUserEmail;
    private String commentUserNickname;
    private String commentUserPhoto;
}
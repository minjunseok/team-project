package moyeora.myapp.service;

import moyeora.myapp.dto.profile.*;
import moyeora.myapp.vo.Post;

import java.util.List;


public interface  MyPageService {
  public List<Post> findNewPost(int no);

  public List<Post> findHotPost(int no);

  public List<Post> findFollowPost(int no);

  public ProfileResponseDTO getProfile(int userNo, int page);

  List<FollowListResponseDTO> followList(FollowListRequestDTO followListRequestDTO);

  int addFollow(FollowRequestDTO followRequestDTO);

  int checkFollow(FollowRequestDTO followRequestDTO);

  List<ProfileResponse2DTO> getFollowerPost(int userNo, int page);

  List<ProfileResponse2DTO> getSchoolPost(int userNo, int page);

  List<ProfileResponse2DTO> getLikePost(int userNo, int page);
}


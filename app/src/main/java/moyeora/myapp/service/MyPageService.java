package moyeora.myapp.service;

import java.util.List;

import moyeora.myapp.dto.profile.FollowListRequestDTO;
import moyeora.myapp.dto.profile.FollowListResponseDTO;
import moyeora.myapp.dto.profile.FollowRequestDTO;
import moyeora.myapp.dto.profile.ProfileResponseDTO;
import moyeora.myapp.vo.Post;


public interface  MyPageService {
  public List<Post> findNewPost(int no);

  public List<Post> findHotPost(int no);

  public List<Post> findFollowPost(int no);

  public ProfileResponseDTO getProfile(int userNo, int page);

  List<FollowListResponseDTO> followList(FollowListRequestDTO followListRequestDTO);

  int addFollow(FollowRequestDTO followRequestDTO);

  int checkFollow(FollowRequestDTO followRequestDTO);

  ProfileResponseDTO getFollowerPost(int userNo, int page);

  ProfileResponseDTO getSchoolPost(int userNo, int page);

  ProfileResponseDTO getLikePost(int userNo, int page);
}


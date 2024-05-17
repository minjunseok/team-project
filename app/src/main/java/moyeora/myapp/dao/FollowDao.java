package moyeora.myapp.dao;

import moyeora.myapp.dto.profile.FollowListResponseDTO;
import moyeora.myapp.dto.profile.FollowRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.MatchesPattern;
import java.util.List;

@Mapper
public interface FollowDao {
    List<FollowListResponseDTO> findFollowingByUserNo(int userNo);

    List<FollowListResponseDTO> findFollowerByUserNo(int userNo);

    int checkFollow(FollowRequestDTO followRequestDTO);

    void deleteFollow(FollowRequestDTO followRequestDTO);

    void addFollow(FollowRequestDTO followRequestDTO);
}

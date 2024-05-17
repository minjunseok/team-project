package moyeora.myapp.dao;

import moyeora.myapp.dto.like.LikeRequestDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeDao {

    int findLike(LikeRequestDTO likeRequestDTO);

    void deleteLike(LikeRequestDTO likeRequestDTO);

    void addLike(LikeRequestDTO likeRequestDTO);
}

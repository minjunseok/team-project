package moyeora.myapp.service.impl;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.LikeDao;
import moyeora.myapp.dto.like.LikeRequestDTO;
import moyeora.myapp.service.LikeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultLikeService implements LikeService {
    private final LikeDao likeDao;
    @Override
    public int like(LikeRequestDTO likeRequestDTO) {
        if (likeDao.findLike(likeRequestDTO)>=1) {
            likeDao.deleteLike(likeRequestDTO);
            return 0;
        } else {
            likeDao.addLike(likeRequestDTO);
            return 1;
        }
    }
}

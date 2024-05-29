package moyeora.myapp.service.impl;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.*;
import moyeora.myapp.dto.profile.*;
import moyeora.myapp.service.MyPageService;
import moyeora.myapp.vo.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultMyPageService implements MyPageService {
  private final PostDao postDao;
  private final UserDao userDao;
  private final FollowDao followDao;
  private final PurchaseDao purchaseDao;
  private final BillingKeyDao billingKeyDao;

  @Override
  public List<Post> findNewPost(int no) {
    System.out.println("@@@@@@@@@@@@@@@@@@@"+postDao.findByUser(no));
    return postDao.findByUser(no);
  }

  @Override
  public List<Post> findHotPost(int no) {
    return postDao.findByLike();
  }

  @Override
  public List<Post> findFollowPost(int no) {
    return postDao.findByFollow(no);
  }


  @Override
  public ProfileResponseDTO getProfile(int userNo, int page) {

    ProfileResponseDTO dto = userDao.findAllPostsByUserNo(userNo, page * 5, (page - 1) * 5);
    if (billingKeyDao.subscriptionTF(userNo) == null || billingKeyDao.subscriptionTF(userNo).isEmpty()) {
      dto.setSubscriptionTF(0);
    } else {
      dto.setSubscriptionTF(1);
    }
    dto.setSubscriptionDate(purchaseDao.findExpiredDate(userNo));
    return dto;
  }

  @Override
  public List<ProfileResponse2DTO> getFollowerPost(int userNo, int page) {

    return userDao.findFollowerPostByUserNo(userNo, page*5, (page-1)*5);
  }

  @Override
  public List<ProfileResponse2DTO> getLikePost(int userNo, int page) {

    return userDao.findLikePostByUserNo(userNo, page*5, (page-1)*5);
  }

  @Override
  public List<ProfileResponse2DTO> getSchoolPost(int userNo, int page) {

    return userDao.findSchoolPostByUserNo(userNo, page*5, (page-1)*5);
  }


  @Override
  public List<FollowListResponseDTO> followList(FollowListRequestDTO followListRequestDTO) {
    List<FollowListResponseDTO> list = followListRequestDTO.getType() == 0 ?
            followDao.findFollowerByUserNo(followListRequestDTO.getUserNo())
            : followDao.findFollowingByUserNo(followListRequestDTO.getUserNo());
    System.out.println(followListRequestDTO);
    if(followListRequestDTO.getClickUserNo()<=0) {
      System.out.println("sddasdasdasd");
      return list;
    }
    List<FollowListResponseDTO> myList = followDao.findFollowingByUserNo(followListRequestDTO.getClickUserNo());
    System.out.println("sddasdasdasd1");
    System.out.println(myList);
    for(FollowListResponseDTO f : list) {
      f.setState(1);
      for(FollowListResponseDTO mf : myList) {
        System.out.println("sddasdasdasd3");
        System.out.println(f.getUserNo()+","+mf.getUserNo());
        if(f.getUserNo()== mf.getUserNo()) {
          f.setState(2);
          break;
        }
      }

      if(f.getUserNo()== followListRequestDTO.getClickUserNo()) {
        f.setState(3);
      }
    }
    return list;
  }


  @Override
  public int addFollow(FollowRequestDTO followRequestDTO) {
    if(followDao.checkFollow(followRequestDTO) > 0) {
      followDao.deleteFollow(followRequestDTO);
      return 0;
    } else {
      followDao.addFollow(followRequestDTO);
      return 1;
    }
  }

  public int checkFollow(FollowRequestDTO followRequestDTO) {
    if(followDao.checkFollow(followRequestDTO) > 0) {
      return 1;
    } else {
      return 0;
    }
  }
}

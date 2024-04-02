package moyeora.myapp.dao;

import moyeora.myapp.vo.InterestTag;
import moyeora.myapp.vo.UserTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserTagDao {

  void add(UserTag tag);

    int addAll(List<UserTag> tags);

    int delete(int tagNo);

    int deleteAll(int userNo);

    List<UserTag> findAllByUserNo(int userNo);

}

package moyeora.myapp.dao;

import moyeora.myapp.vo.UserTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserTagDao {

  void add(@Param("tagNo") int tagNo, @Param("userNo") int userNo);


}

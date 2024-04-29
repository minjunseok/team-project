package moyeora.myapp.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassUserDao {

    void add(@Param("userNo") int userNo, @Param("classNo") int classNo, @Param("schoolNo") int schoolNo);
}
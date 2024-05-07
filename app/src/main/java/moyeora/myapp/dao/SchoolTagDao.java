package moyeora.myapp.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SchoolTagDao {
  void add(@Param("tagNo") int tagNum, @Param("schoolNo") int schoolNo);

  int deleteSchoolTags(int schoolNo);
}
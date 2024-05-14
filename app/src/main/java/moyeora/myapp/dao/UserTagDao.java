package moyeora.myapp.dao;

import moyeora.myapp.dto.admin.statistics.AdminUserHobbyResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserTagDao {

  void add(@Param("tagNo") int tagNo, @Param("userNo") int userNo);

  int update(@Param("tagNo") int tagNum, @Param("userNo") int userNo);

 int  deleteAllUserTagNo(int no);

    List<AdminUserHobbyResponseDTO> findGroupByHobby();
}
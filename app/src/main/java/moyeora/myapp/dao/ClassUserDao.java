package moyeora.myapp.dao;

import moyeora.myapp.dto.schoolclass.SchoolClassRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassUserDao {


    void insert(SchoolClassRequestDTO schoolClassRequestDTO);

    int countMember(@Param("classNo") int classNo, @Param("userNo") int userNo);

    void memberDelete(SchoolClassRequestDTO schoolClassRequestDTO);
}
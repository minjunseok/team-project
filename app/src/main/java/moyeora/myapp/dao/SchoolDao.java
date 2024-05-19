package moyeora.myapp.dao;



import moyeora.myapp.dto.admin.school.AdminSchoolListResponseDTO;
import moyeora.myapp.dto.school.admin.SchoolOpenRangeUpdateRequestDTO;
import moyeora.myapp.dto.schoolclass.ClassDeleteDTO;
import moyeora.myapp.vo.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SchoolDao {

  public List<School> findHotSchool(int userNo);

  void add(School school);

  int delete(int schoolNo);

  List<School> findAll(
          @Param("category_no") int categoryNo,
          @Param("offset") int offset,
          @Param("rowCount") int rowCount);

  School findBy(int postNo);

  int update(School school);

  int countAll(int categoryNo);
  public List<School> findBySchoolName(@Param("name") String name);

  public School findByNo(int no);

  public void stopSchool(int no);

  public void updateSchoolOpenRange(SchoolOpenRangeUpdateRequestDTO schoolOpenRangeUpdateRequestDTO);

  List<AdminSchoolListResponseDTO> findAllByPageSize(int offset, int limit);

  void updateBlackList(int schoolNo, LocalDate date);

  List<AdminSchoolListResponseDTO> findBySchoolInfo(String schoolInfo);

  void updateLimitedMan(int userNo, int limitedMan);

  int isNameExists(String name);

  School findBySchool(int schoolNo);

  int deleteSchool(int schoolNo);

  School findBySchoolNo(int schoolNo);

  //해당 스쿨 gm 삭제
  int deleteSchoolGm(int schoolNo);


}


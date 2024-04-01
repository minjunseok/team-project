package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.School;
import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SchoolDao {
  public SchoolUser findByUserNo(int no);

  void add(School school);

  int delete(int schoolNo);

  List<School> findAll(
      @Param("category_no") int categoryNo,
      @Param("offset") int offset,
      @Param("rowCount") int rowCount);

  School findBy(int postNo);

  int update(School school);

  int countAll(int categoryNo);

}

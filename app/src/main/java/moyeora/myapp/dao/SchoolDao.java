package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.School;
import org.apache.ibatis.annotations.Param;

public interface SchoolDao {

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

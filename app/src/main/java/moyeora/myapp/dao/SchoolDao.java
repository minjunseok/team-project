package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.School;
import org.apache.ibatis.annotations.Param;

public interface SchoolDao {

  void add(School school);

  int delete(int school_no);

  List<School> findAll(
      @Param("category_no") int no,
      @Par

  )

}

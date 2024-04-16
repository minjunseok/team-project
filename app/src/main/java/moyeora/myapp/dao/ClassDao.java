package moyeora.myapp.dao;


import java.util.List;
import moyeora.myapp.vo.SchoolClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassDao {
  public List<SchoolClass> findByDate(@Param("date") String date);

  public List<SchoolClass>  findByUserAddress(String address);
}

package moyeora.myapp.dao;


import java.util.List;
import moyeora.myapp.vo.Class;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassDao {
  public List<Class> findByDate(@Param("date") String date);
}

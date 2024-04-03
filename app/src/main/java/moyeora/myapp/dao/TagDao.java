package moyeora.myapp.dao;

import java.util.List;
import moyeora.myapp.vo.SchoolUser;
import moyeora.myapp.vo.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagDao {
  public List<Tag> findAll();
}

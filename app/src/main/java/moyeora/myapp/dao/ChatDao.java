package moyeora.myapp.dao;

import moyeora.myapp.vo.Gm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatDao {

    void save(Gm gm);
}

package moyeora.myapp.dao;


import moyeora.myapp.vo.Alert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlertDao {

    void addAlert(Alert alert);
}

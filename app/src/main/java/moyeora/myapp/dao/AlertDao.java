package moyeora.myapp.dao;

import moyeora.myapp.vo.Alert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AlertDao {

    void addAlert(Alert alert);

    List<Alert> findAll(int no);

    List<Alert> findUnreadAlertList(int no);

    void updateIsRead(int no);

    void updateAllIsRead(int no);
}
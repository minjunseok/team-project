package moyeora.myapp.dao;


import java.util.List;
import moyeora.myapp.vo.Dm;
import moyeora.myapp.vo.DmRoom;
import moyeora.myapp.vo.Gm;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ChatDao {

  void saveGm(Gm gm);

  void saveDm(Dm dm);

  List<Gm> findGmListBySchoolNo(int no);

  List<Dm> findDmListBySenderAndReceiver(int sender, int receiver);

  List<Dm> findDmListByRoomNo(int no);

  void addDmRoom(DmRoom room);

  DmRoom findDmRoomByNo(int no);

  DmRoom findDmRoomByUserNo(int user1, int user2);

}

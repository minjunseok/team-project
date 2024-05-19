package moyeora.myapp.dao;


import moyeora.myapp.vo.Dm;
import moyeora.myapp.vo.DmRoom;
import moyeora.myapp.vo.Gm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ChatDao {

  void saveGm(Gm gm);

  Gm getGm(int no);

  void saveDm(Dm dm);

  Dm getDm(int no);

  List<Gm> findGmListBySchoolNo(int no);

  List<Dm> findDmListBySenderAndReceiver(int sender, int receiver);

  List<Dm> findDmListByRoomNo(int no);

  void addDmRoom(DmRoom room);

  DmRoom findDmRoomByNo(int no);

  DmRoom findDmRoomByUserNo(int user1, int user2);

  List<Gm> getGmListOnlyLast(int no);

  List<Dm> getDmListOnlyLast(int no);

}
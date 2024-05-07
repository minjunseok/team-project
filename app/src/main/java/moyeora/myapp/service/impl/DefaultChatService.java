package moyeora.myapp.service.impl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.ChatDao;
import moyeora.myapp.vo.Dm;
import moyeora.myapp.vo.DmRoom;
import moyeora.myapp.vo.Gm;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultChatService {

    private final ChatDao chatDao;

    public void saveGm(Gm gm) {
        chatDao.saveGm(gm);
    }

    public void saveDm(Dm dm) {
        chatDao.saveDm(dm);
    }

    public List<Gm> getGmList(int no) {
        return chatDao.findGmListBySchoolNo(no);
    }

    public List<Dm> getDmList(int sender, int receiver) {
        return chatDao.findDmListBySenderAndReceiver(sender,receiver);
    }

    public List<Dm> getDmList(int no) {
        return chatDao.findDmListByRoomNo(no);
    }

    public void addDmRoom(DmRoom room) {
        chatDao.addDmRoom(room);
    }

    public DmRoom getDmRoom(int no) {
        return chatDao.findDmRoomByNo(no);
    }

    public DmRoom getDmRoom(int sender, int receiver) {
        return chatDao.findDmRoomByUserNo(sender,receiver);

    }
}

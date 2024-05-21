package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.ChatDao;
import moyeora.myapp.vo.Dm;
import moyeora.myapp.vo.DmRoom;
import moyeora.myapp.vo.Gm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultChatService {

    private final ChatDao chatDao;
    private final DefaultNotificationService notificationService;

    @Transactional
    public void saveGm(Gm gm) throws Exception {
        chatDao.saveGm(gm);
/*        Alert alert = new Alert();
        alert.setUserNo(gm.getSender().getNo()); // 알림 수신자 번호
        alert.setName("알림 타이틀"); // 알림 타이틀 문구
        alert.setContent("타이틀 밑에 영역에 넣을 상세 내용"); // 알림 내용 문구
        alert.setPhoto("알림 썸네일에 들어갈 사진 경로(NCD 사용 기준)"); // 사진 파일명
        alert.setType(1); // 알림 타입 1.좋아요 2.댓글 3.팔로잉
        alert.setRedirectPath("/클릭시 이동할 경로"); // 리다이렉트경로 지정
        notificationService.add(alert);*/
    }

    public Gm getGm(int no) {
        return chatDao.getGm(no);
    }

    public void saveDm(Dm dm) {
        chatDao.saveDm(dm);
    }

    public Dm getDm(int no) {
        return chatDao.getDm(no);
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

    public List<Gm> getGmListOnlyLast(int no) {
        return chatDao.getGmListOnlyLast(no);
    }

    public List<Dm> getDmListOnlyLast(int no) {
        return chatDao.getDmListOnlyLast(no);
    }
}

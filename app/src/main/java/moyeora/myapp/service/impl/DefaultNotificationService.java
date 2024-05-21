package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.AlertDao;
import moyeora.myapp.vo.Alert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultNotificationService {

    private static final Log log = LogFactory.getLog(DefaultNotificationService.class);
    private final AlertDao alertDao;
    private final SimpMessageSendingOperations operations;

    private static final String ALERT_TYPE_1 = "LIKE";
    private static final String ALERT_TYPE_2 = "COMMENT";
    private static final String ALERT_TYPE_3 = "FOLLOWER";

    @Value("${ncp.storage.bucket}")
    private String bucketname;
    @Value("${ncp.storage.endpoint}")
    private String endpoint;


    @Transactional
    public Alert add(Alert alert) throws Exception {
        String NCD_PATH = this.bucketname + "/" + this.endpoint;
        HashMap<Integer, String> BUCKET_PATH = new HashMap<>() {{
            put(1, NCD_PATH + "/school");
            put(2, NCD_PATH + "/school");
            put(3, NCD_PATH + "/user");
        }};
        alert.setFilePath(BUCKET_PATH.get(alert.getType()));
        alertDao.addAlert(alert);
        operations.convertAndSend("/sub/user/" + alert.getUserNo(), alert);
        log.info("pub ok");
        return alert;
    }

    public List<Alert> findAll(int no) {
        return alertDao.findAll(no);
    }

    public List<Alert> findUnreadAlertList(int no) {
        return alertDao.findUnreadAlertList(no);
    }

    public void updateIsRead(int no) {
        alertDao.updateIsRead(no);
    }

    public void updateAllIsRead(int no) {
        alertDao.updateAllIsRead(no);
    }
}

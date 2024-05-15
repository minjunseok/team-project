package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.AlertDao;
import moyeora.myapp.vo.Alert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private static final String NCD_PATH = "https://kr.object.ncloudstorage.com/moyeorastorage";
    private static final HashMap<Integer, String> BUCKET_PATH = new HashMap<Integer, String>() {{
        put(1, "/school");
        put(2, "/school");
        put(3, "/user");
    }};

    @Transactional
    public Alert add(Alert alert) throws Exception {
        alert.setFilePath(NCD_PATH + BUCKET_PATH.get(alert.getType()));
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

    public void update(int no) {
        alertDao.update(no);
    }
}

package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.AlertDao;
import moyeora.myapp.vo.Alert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultNotificationService {

    private static final Log log = LogFactory.getLog(DefaultNotificationService.class);
    private final AlertDao alertDao;
    private final SimpMessageSendingOperations operations;

    @Transactional
    public Alert add(Alert alert) {
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

package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.ChatDao;
import moyeora.myapp.vo.Gm;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultChatService {

    private final ChatDao chatDao;

    public void save(Gm gm) {
        chatDao.save(gm);
    }

}

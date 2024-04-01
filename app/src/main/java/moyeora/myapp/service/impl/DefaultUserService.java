package moyeora.myapp.service.impl;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DefaultUserService implements UserService {


    @Override
    public void add(User user) {

    }

    @Override
    public List<User> list() {
        return null;
    }

    @Override
    public User get(int no) {
        return null;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(int no) {
        return 0;
    }
}

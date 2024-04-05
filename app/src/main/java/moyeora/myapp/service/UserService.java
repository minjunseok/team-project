package moyeora.myapp.service;

import moyeora.myapp.vo.User;
public interface UserService {

    void add(User user);

    User get(int no);
}

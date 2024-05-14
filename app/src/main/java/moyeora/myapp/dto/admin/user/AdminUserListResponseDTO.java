package moyeora.myapp.dto.admin.user;

import moyeora.myapp.vo.User;

import java.time.LocalDate;

public class AdminUserListResponseDTO extends User {

    private String user_no;
    private String name;
    private String grade;
    private String nickname;
    private String email;
    private LocalDate created_at;
    private LocalDate stop_date;

}

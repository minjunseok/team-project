package moyeora.myapp.dao;



import moyeora.myapp.dto.admin.statistics.AdminUserBirthResponseDTO;
import moyeora.myapp.dto.admin.statistics.AdminUserGenderResponseDTO;
import moyeora.myapp.dto.admin.statistics.AdminUserLocalResponseDTO;
import moyeora.myapp.dto.admin.user.AdminUserListResponseDTO;
import moyeora.myapp.dto.profile.ProfileResponseDTO;
import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDate;
import java.util.List;



@Mapper
public interface UserDao {
    public void add(User user);

    public User findBy(int no);

    public int findUserGrade(int grade);

    public void updateGrade(int no, int grade);

    List<String> findEmailByUserList(List<Integer> userList);

    public void downGrade(List<Integer> userList);


    void save(User user);

    int delete(int no);

    List<User> findAll();

    User findByNo(int no);

    int update(User user);

    int updatePassword(User user);

    User findByEmail(String email);

    User findOAuth2User(String email, String provider);

    String findByNameAndPhone(
            @Param("name") String name,
            @Param("phone") String phone);

  public List<AdminUserListResponseDTO> findAllNoMaster(int offset ,int limit);

    void updateBlackList(int userNo, LocalDate date);

    void updateRole(int userNo, int auth);

    List<AdminUserListResponseDTO> findByUserInfo(String userInfo);

    List<AdminUserGenderResponseDTO> findGroupByGender();

    List<AdminUserBirthResponseDTO> findGroupByBirth();

    List<AdminUserLocalResponseDTO> findGroupByLocal();

    public List<User> findAllNoMaster(int limit);

    public int passwordUpdate(User user);

    ProfileResponseDTO findAllPostsByUserNo(int userNo, int limit, int offset);

    ProfileResponseDTO findFollowerPostByUserNo(int userNo, int limit, int offset);

    ProfileResponseDTO findLikePostByUserNo(int userNo, int limit, int offset);

    ProfileResponseDTO findSchoolPostByUserNo(int userNo, int limit, int offset);

}

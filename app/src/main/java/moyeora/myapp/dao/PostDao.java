package moyeora.myapp.dao;

import moyeora.myapp.vo.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostDao {

    void add(Post post);

    List<Post> findAll(@Param("categoryNo") int categoryNo);

    int delete(@Param("no") int no, @Param("schoolNo") int schoolNo);

    Post findBy(int no);

    int update(Post post);

    int countAll(int categoryNo);

    public List<Post> findByLike();

    public List<Post> findByFollow();

    public List<Post> findByUser(int no);

    public List<Post> findByFollow(int no);

    String findByPost(int schoolNo, String content);


    public List<Post> findBySchoolPost();

    // 필터 내용으로 검색했을 때
    List<Post> findBySchoolContent(int schoolNo, @Param("keyword") String keyword);

    // 필터 작성자로 검색했을 때
    List<Post> findBySchoolUserName(int schoolNo, @Param("keyword") String keyword);

    List<Post> findBySchool(int schoolNo);

    List<Post> findBySchoolUserName(@Param("keyword") String keyword);
//  Post findBy(int no);

    Post findByPost(int no, int schoolNo);


    List<Post> findBySchoolPostList(int schoolNo);

}

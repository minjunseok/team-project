package moyeora.myapp.dao;

import moyeora.myapp.vo.SchoolMember;
import moyeora.myapp.vo.SchoolUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchoolMemberDao {
   List<SchoolMember> findByClassMember(int classNo);


}

package moyeora.myapp.dao;

import moyeora.myapp.vo.AttachedFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachedFileDao {

  void add(AttachedFile file);

  int addAll(List<AttachedFile> files);

  int delete(int no);

//  int deleteAll(int postNo);

  List<AttachedFile> findByPostFiles(int no);

  List<AttachedFile> findAllByPostNo(int no);

  AttachedFile findByNo(int no);
}

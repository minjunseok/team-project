package moyeora.myapp.dao;

import moyeora.myapp.vo.AttachedFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttachedFileDao {

  void add(AttachedFile file);

  int addAll(List<AttachedFile> files);

  int delete(int no);

  int deleteAll(int postNo);

  List<AttachedFile> findAllByPostNo(int postNo);

  AttachedFile findByNo(int no);
}

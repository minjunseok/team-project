package moyeora.myapp.service;

import java.util.List;
import moyeora.myapp.vo.AttachedFile;
import moyeora.myapp.vo.School;

public interface SchoolService {

  void add(School school);

  List<School> list(int pageNo, int pageSize);

  School get(int school_no);

  List<AttachedFile> getAttachedFiles(int school_no);

  AttachedFile getAttachedFile(int file_no);

  int deleteAttachedFile(int file_no);

  int countAll(int category);





}

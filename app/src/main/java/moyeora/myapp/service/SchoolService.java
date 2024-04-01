package moyeora.myapp.service;

import java.util.List;

import moyeora.myapp.vo.School;

public interface SchoolService {

  void add(School school);

  List<School> list(int categoryNo, int pageNo, int pageSize);

  School get(int schoolNo);

  int update(School school);

  int delete(int postNo);

  int countAll(int categoryNo);





}

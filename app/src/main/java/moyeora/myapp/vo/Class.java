package moyeora.myapp.vo;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class Class {
  private int no;
  private int schoolNo;
  private int userNo;
  private String name;
  private String security;
  private String content;
  private String location;
  private String nowAt;
  private String createdAt;
  private String endedAt;
  private String member;
  private String repeatSet;
  private String photo;

}

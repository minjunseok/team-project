package moyeora.myapp.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Data;

@Data
public class DmRoom implements Serializable {

  private static final long serialVersionUID = 100L;

  private int no;
  private int user1;
  private int user2;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Timestamp createdDate;

  @Builder
  public DmRoom() {
  }

  @Builder
  public DmRoom(int no, int user1, int user2) {
    this.no = no;
    this.user1 = user1;
    this.user2 = user2;
  }

  @Builder
  public DmRoom(int user1, int user2) {
    this.user1 = user1;
    this.user2 = user2;
  }

  @Builder
  public DmRoom(int no, int user1, int user2, Timestamp createdDate) {
    this.no = no;
    this.user1 = user1;
    this.user2 = user2;
    this.createdDate = createdDate;
  }
}

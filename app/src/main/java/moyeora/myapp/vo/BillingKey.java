package moyeora.myapp.vo;


import java.sql.Date;
import java.time.LocalDate;
import lombok.Data;

@Data
public class BillingKey {
  private int no;
  private int userNo;
  private String billingKey;
  private Date nextBillingDate;
  private int billingErrorCount;
  private int price;
  private String content;


}

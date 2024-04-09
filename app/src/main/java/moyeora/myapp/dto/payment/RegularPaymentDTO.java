package moyeora.myapp.dto.payment;


import java.util.Date;
import lombok.Data;

@Data
public class RegularPaymentDTO {
  private String billingKey;
  private String method;
  private String content;
  private int userNo;
  private String receiptNo;
  private Date nextBillingDate;
  private int price;
}

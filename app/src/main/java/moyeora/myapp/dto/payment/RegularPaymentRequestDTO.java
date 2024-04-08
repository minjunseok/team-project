package moyeora.myapp.dto.payment;


import java.sql.Date;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegularPaymentRequestDTO {
  private String billingKey;
  private String method;
  private String content;
  private int userNo;
  private String receiptNo;
  private Date nextBillingDate;
  private int price;
}

package moyeora.myapp.dto.payment;

import java.sql.Date;
import java.time.LocalDate;
import lombok.Data;


@Data
public class RegularPaymentResponseDTO {
  private String message;
  private Date nextBillingDate;

}

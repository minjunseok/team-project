package moyeora.myapp.dto.payment;


import lombok.Data;

@Data
public class RegularPaymentDTO {

  private String billingKey;
  private String method;
  private String name;
  private int userNo;
}

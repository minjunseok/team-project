package moyeora.myapp.vo;

import java.time.LocalDate;
import java.util.Date;

public class Purchase {
  private int no;
  private int userNo;
  private int price;
  private Date purchaseAt;
  private String paymentType;
  private String content;
  private java.sql.Date expirationDate;
  private String receiptNo;
}

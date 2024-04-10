package moyeora.myapp.service;

import java.sql.Date;
import java.util.List;
import moyeora.myapp.dto.payment.RegularPaymentRequestDTO;
import moyeora.myapp.vo.BillingKey;

public interface PaymentService {
  void purchase(RegularPaymentRequestDTO regularPaymentRequestDTO) throws Exception;

  void billingKeySave(RegularPaymentRequestDTO regularPaymentRequestDTO);
  public List<BillingKey> billingKeyFindByDate(Date date);
  public void billingKeyUpdateDate(Date date, int userNo);

  List<BillingKey> findErrorCount();
  public void stopSubscribe(int userNo);

  public void deleteByError();

  public void deleteBillingKeyByUserNo(int userNo);

  public int findBillingKeyByUserNo(int userNo);
}

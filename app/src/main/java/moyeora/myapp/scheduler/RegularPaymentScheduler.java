package moyeora.myapp.scheduler;


import java.awt.desktop.SystemEventListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.dao.BillingKeyDao;
import moyeora.myapp.dto.payment.RegularPaymentRequestDTO;
import moyeora.myapp.service.PaymentService;
import moyeora.myapp.vo.BillingKey;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class RegularPaymentScheduler {
  private final PaymentService paymentService;
  @Scheduled(cron = ("0 3 18 * * *"))
  public void regularPaymentCron() throws Exception {
    //해당 날짜의 빌링키를 가져오고
    Calendar c = Calendar.getInstance();
    Date date = new Date(c.getTimeInMillis());
    //DTO에 저장 후
    List<BillingKey> arr = paymentService.billingKeyFindByDate(date);
    //구매한다
    System.out.println(arr);
    for(BillingKey bi : arr) {
      RegularPaymentRequestDTO requestDTO = RegularPaymentRequestDTO.builder()
        .price(bi.getPrice())
        .billingKey(bi.getBillingKey())
        .nextBillingDate(bi.getNextBillingDate())
        .content(bi.getContent())
        .userNo(bi.getUserNo())
        .build();
      try {
        paymentService.purchase(requestDTO);
        paymentService.billingKeyUpdateDate(date);
      } catch (Exception e) {
        //환불해야함
      }
    }

  }
}

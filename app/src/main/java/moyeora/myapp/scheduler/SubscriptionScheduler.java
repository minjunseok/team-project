package moyeora.myapp.scheduler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionScheduler {
  //결제 실패 시 메일을 보냅니다
  @Scheduled(cron = ("0 0 7 * * *"))
  public void regularPaymentFailMail() {

  }

  //결제 3회이상 실패 시 등급 하락과 동시에 혜택 하락시킵니다.
  @Scheduled(cron = ("0 0 8 * * *"))
  public void regularGradeSetting() {

  }

}

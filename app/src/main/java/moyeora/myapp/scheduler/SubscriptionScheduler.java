package moyeora.myapp.scheduler;


import java.sql.Date;
import java.util.Calendar;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SubscriptionScheduler {

  @Scheduled(cron = ("0 0 7 * * *"))
  public void regularPaymentFailMail() {

  }

  @Scheduled(cron = ("0 0 8 * * *"))
  public void regularGradeSetting() {
    Calendar c = Calendar.getInstance();
    Date date = new Date(c.getTimeInMillis());

  }

}

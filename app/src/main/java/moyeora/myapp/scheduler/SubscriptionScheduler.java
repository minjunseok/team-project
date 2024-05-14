package moyeora.myapp.scheduler;


import lombok.RequiredArgsConstructor;
import moyeora.myapp.service.PaymentService;
import moyeora.myapp.service.SchoolService;
import moyeora.myapp.service.UserService;
import moyeora.myapp.service.impl.DefaultMailService;
import moyeora.myapp.vo.BillingKey;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubscriptionScheduler {
  private final DefaultMailService mailService;
  private final PaymentService paymentService;
  private final UserService userService;
  private final SchoolService schoolService;

  @Scheduled(cron = ("0 0 7 * * *"))
  public void regularPaymentFail() {
    System.out.println(paymentService.findBillingKeyByUserNo(3));
    List<Integer> memberLists = new ArrayList<>();
    List<Integer> stopMemberList = new ArrayList<>();
    for(BillingKey bi :paymentService.findErrorCount()) {
      memberLists.add(bi.getUserNo());
      if(bi.getBillingErrorCount()==1) {
        stopMemberList.add(bi.getUserNo());
      }
    }
    userService.downGrade(stopMemberList);
    userService.findEmailByUserList(stopMemberList);
    this.stopSchool(schoolService.findSchoolNoByUserNo(stopMemberList));
    this.mailForStopUser(userService.findEmailByUserList(memberLists));
  }

  @Scheduled(cron = ("0 0 8 * * *"))
  public void regularGradeSetting() {
    paymentService.deleteByError();
  }

  public void mailForStopUser(List<String> emails) {
    String subject = "[moyeora] 정기구독 결제 실패입니다";
    String content = "잔액 부족, 카드 정지 등의 이유로 정기구독 결제에 실패하였습니다. 무료 요금제 외의 기능은 중단되며 7일 후에 중지된 데이터는 삭제됩니다";
    for(String email : emails) {
      //mailService.sandEmail(email,subject,content);
    }
  }

  public void stopSchool(List<List<Integer>> schools) {
    for(List<Integer> lists : schools) {
      if(lists.size() > 1) {
        for(int i = 1; i<lists.size(); i++) {
          schoolService.stopSchool(lists.get(i));
        }
      }
    }
  }

}

package moyeora.myapp.service.impl;

import java.nio.charset.StandardCharsets;
import moyeora.myapp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class DefaultMailService implements MailService {
  @Autowired
  private JavaMailSender mailSender;
  private int authCode;
  public void createCode() {
    Random r = new Random();
    String randomNumber = "";
    for(int i = 0; i < 6; i++) {
      randomNumber += Integer.toString(r.nextInt(10));
    }
    authCode = Integer.parseInt(randomNumber);
  }

  public String setText(String to) {
    createCode();
    String subject = "[moyeora] authentication code";
    String text =
        "[moyeora] 비밀번호 확인 인증" +
            "<br><br>" + "인증코드 : " + authCode + "<br>";
    sandEmail(to, subject, text);
    return Integer.toString(authCode);
  }

  public void sandEmail(String to, String subject, String text) {
    MimeMessage message = mailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message,true, StandardCharsets.UTF_8.name());
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(text,true);
      mailSender.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
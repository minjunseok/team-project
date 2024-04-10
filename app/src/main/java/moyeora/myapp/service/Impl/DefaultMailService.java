package moyeora.myapp.service.Impl;

import java.nio.charset.StandardCharsets;
import moyeora.myapp.service.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Service
public class DefaultMailService {

  @Value("${spring.mail.username}")
  private String configEmail;
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private RedisUtil redisUtil;

  private String subject = "[moyeora] authentication code";

  private String setContext(String authCode) {
    Context context = new Context();
    TemplateEngine templateEngine = new SpringTemplateEngine();
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    context.setVariable("code", authCode);

    templateResolver.setPrefix("templates/mail/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCacheable(false);

    templateEngine.setTemplateResolver(templateResolver);

    return templateEngine.process("mail", context);
  }

  private MimeMessage createEmailForm(String to, String code) throws MessagingException {

    MimeMessage message = mailSender.createMimeMessage();
    message.addRecipients(MimeMessage.RecipientType.TO, to);
    message.setSubject(subject);
    message.setFrom(configEmail);
    message.setText(setContext(code), StandardCharsets.UTF_8.name(), "html");

    return message;
  }

  @Transactional
  public void sendEmail(String to, String code, String authId) throws MessagingException {
    if (redisUtil.existData(to)) {
      redisUtil.deleteData(to);
    }
    MimeMessage emailForm = createEmailForm(to, code);
    mailSender.send(emailForm);
  }
}
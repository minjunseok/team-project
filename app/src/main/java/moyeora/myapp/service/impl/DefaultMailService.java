package moyeora.myapp.service.impl;

import moyeora.myapp.security.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class DefaultMailService {

  @Value("${spring.mail.username}")
  private String configEmail;
  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  private RedisUtil redisUtil;

  private String setContext(String code, String template) {
    Context context = new Context();
    TemplateEngine templateEngine = new SpringTemplateEngine();
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    context.setVariable("code", code);

    templateResolver.setPrefix("templates/mail/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode(TemplateMode.HTML);
    templateResolver.setCacheable(false);

    templateEngine.setTemplateResolver(templateResolver);

    return templateEngine.process(template, context);
  }

  private MimeMessage createEmailForm(String to, String subject, String code, String template) throws MessagingException {

    MimeMessage message = mailSender.createMimeMessage();
    message.addRecipients(MimeMessage.RecipientType.TO, to);
    message.setSubject(subject);
    message.setFrom(configEmail);
    message.setText(setContext(code, template), StandardCharsets.UTF_8.name(), "html");

    return message;
  }

  @Transactional
  public void sendEmail(String to, String subject, String code, String authId, String template) throws MessagingException {
    if (redisUtil.existData(authId)) {
      redisUtil.deleteData(authId);
    }
    MimeMessage emailForm = createEmailForm(to, subject, code, template);
    mailSender.send(emailForm);
  }
}
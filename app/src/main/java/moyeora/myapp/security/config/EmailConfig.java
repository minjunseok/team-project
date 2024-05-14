package moyeora.myapp.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private int port;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.properties.mail.smtp.auth}")
  private boolean auth;

  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private boolean starttlsEnable;

  @Value("${spring.mail.properties.mail.smtp.starttls.required}")
  private boolean starttlsRequired;

//  @Value("${spring.mail.properties.mail.smtp.connectiontimeout}")
//  private int connectionTimeout;
//
//  @Value("${spring.mail.properties.mail.smtp.timeout}")
//  private int timeout;
//
//  @Value("${spring.mail.properties.mail.smtp.writetimeout}")
//  private int writeTimeout;

  @Bean
  public JavaMailSender mailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);
    Properties mailProperties = getMailProperties();
    mailSender.setJavaMailProperties(mailProperties);
    return mailSender;
  }

  private Properties getMailProperties() {
    Properties properties = new Properties();
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.debug", "true");
    properties.put("mail.smtp.ssl.trust", "smtp.naver.com");
    properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
    return properties;
  }
}
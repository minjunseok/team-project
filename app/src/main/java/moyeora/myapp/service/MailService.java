package moyeora.myapp.service;

public interface MailService {
  public void createCode();

  public String setText(String to);

  public void sandEmail(String to, String subject, String text);

}

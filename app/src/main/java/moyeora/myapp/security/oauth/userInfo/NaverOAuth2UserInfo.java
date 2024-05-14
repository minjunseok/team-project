package moyeora.myapp.security.oauth.userInfo;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NaverOAuth2UserInfo implements OAuth2UserInfo {

  private Map<String, Object> attributes; // oauth2User.getAttributes()

  public NaverOAuth2UserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  @Override
  public String getProvider() {
    return "naver";
  }

  @Override
  public String getProviderId() {
    return (String) attributes.get("id");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email").toString();
  }

  @Override
  public String getUsername() {
    return (String) attributes.get("name");
  }

  public String getMobile() {
    return (String) attributes.get("mobile");
  }

  public String getProfileImage() {return (String) attributes.get("profile_image");}

  public String getBirthDay() {return (String) attributes.get("birthyear") + "-" + (String) attributes.get("birthday");}
}
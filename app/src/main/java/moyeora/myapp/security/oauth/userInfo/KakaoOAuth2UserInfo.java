package moyeora.myapp.security.oauth.userInfo;

import lombok.RequiredArgsConstructor;


import java.util.Map;
import lombok.RequiredArgsConstructor;


import java.util.Map;

@RequiredArgsConstructor
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {
  private Map<String, Object> attributes;
  private Map<String, Object> kakaoAccountAttributes;
  private Map<String, Object> profileAttributes;

  public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
    this.attributes = attributes;
    this.kakaoAccountAttributes = (Map<String, Object>) attributes.get("kakao_account");
    this.profileAttributes = (Map<String, Object>) kakaoAccountAttributes.get("profile");
  }

  @Override
  public String getProvider() {
    return "kakao";
  }

  @Override
  public String getProviderId() {
    return String.valueOf(attributes.get("id"));
  }

  @Override
  public String getEmail() {
    return (String) kakaoAccountAttributes.get("email");
  }

  @Override
  public String getUsername() {
    return (String) profileAttributes.get("nickname");
  }
}
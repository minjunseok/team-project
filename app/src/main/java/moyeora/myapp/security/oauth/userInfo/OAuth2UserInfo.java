package moyeora.myapp.security.oauth.userInfo;

public interface OAuth2UserInfo {
  String getProvider();
  String getProviderId();
  String getEmail();
  String getUsername();
}
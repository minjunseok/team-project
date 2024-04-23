package moyeora.myapp.security.OAuth.userInfo;

public interface OAuth2UserInfo {
  String getProvider();
  String getProviderId();
  String getEmail();
  String getUsername();
}

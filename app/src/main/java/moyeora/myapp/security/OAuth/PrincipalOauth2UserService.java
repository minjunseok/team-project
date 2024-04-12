package moyeora.myapp.security.OAuth;

import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import moyeora.myapp.security.OAuth.userInfo.NaverOAuth2UserInfo;
import moyeora.myapp.security.OAuth.userInfo.OAuth2UserInfo;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

  private final UserService userService;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());

    OAuth2UserInfo oAuth2UserInfo = null;
    String provider = userRequest.getClientRegistration().getRegistrationId();

    if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
      System.out.println("네이버 로그인 요청");
      oAuth2UserInfo = new NaverOAuth2UserInfo((Map) oAuth2User.getAttributes().get("response"));
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakako")) {
      //oAuth2UserInfo = new KakaoOAuth2UserInfo((Map) oAuth2User.getAttributes().get("response"));
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("goolge")) {
      //oAuth2UserInfo = new GoogleOAuth2UserInfo((Map) oAuth2User.getAttributes().get("response"));
    } else {
      throw new OAuth2AuthenticationException("[로그인 에러] 요청하신 로그인은 지원되지 않습니다.");
    }

    assert oAuth2UserInfo != null;
    User user = userService.findByEmail(oAuth2UserInfo.getEmail());

    if (user == null) {
      user = User.builder()
          .email(oAuth2UserInfo.getEmail())
          .phone(oAuth2UserInfo.getMobile())
          .name(oAuth2UserInfo.getUsername())
          .providerId(oAuth2UserInfo.getProviderId())
          .provider(oAuth2UserInfo.getProvider())
          .createdAt(Timestamp.valueOf(LocalDateTime.now()))
          .build();
      System.out.println(user.toString());
      throw new OAuth2AuthenticationException("회원 정보 없음");
    }
    return new PrincipalDetails(user,oAuth2User.getAttributes());
  }
}

package moyeora.myapp.security.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moyeora.myapp.security.PrincipalDetails;
import moyeora.myapp.security.oauth.userInfo.GoogleOAuth2UserInfo;
import moyeora.myapp.security.oauth.userInfo.KakaoOAuth2UserInfo;
import moyeora.myapp.security.oauth.userInfo.NaverOAuth2UserInfo;
import moyeora.myapp.security.oauth.userInfo.OAuth2UserInfo;
import moyeora.myapp.security.util.RedisUtil;
import moyeora.myapp.service.UserService;
import moyeora.myapp.vo.User;
import moyeora.myapp.vo.role.Role;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

  private final UserService userService;
  private final RedisUtil redisUtil;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);
    log.info("getAttributes : {}", oAuth2User.getAttributes());

    OAuth2UserInfo oAuth2UserInfo = null;

    if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
      oAuth2UserInfo = new NaverOAuth2UserInfo((Map) oAuth2User.getAttributes().get("response"));
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
      oAuth2UserInfo = new KakaoOAuth2UserInfo(oAuth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
      oAuth2UserInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
    } else {
      throw new OAuth2AuthenticationException(new OAuth2Error("OAuth2Error"),"요청하신 로그인 서비스는 지원되지 않습니다.");


    }

    String email = oAuth2UserInfo.getEmail();
    String name = oAuth2UserInfo.getUsername();
    String providerId = oAuth2UserInfo.getProviderId();
    String provider = oAuth2UserInfo.getProvider();
    String role = Role.USER.getKey();

    User user = userService.findOAuth2User(email, provider);
    if (user == null) {
      if (userService.findByEmail(email) == null) {
        user = User.builder()
            .email(email)
            .name(name)
            .providerId(providerId)
            .provider(provider)
            .role(role)
            .build();
      } else {
        throw new OAuth2AuthenticationException(new OAuth2Error("OAuth2Error"),"이미 가입된 이메일입니다.");
      }

      try {
        String key = user.getEmail() + "_" + provider + "_" + providerId;
        redisUtil.setDataExpire(key,user);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
    return new PrincipalDetails(user,oAuth2User.getAttributes());
  }
}

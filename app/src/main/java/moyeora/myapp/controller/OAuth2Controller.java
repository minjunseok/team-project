package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import moyeora.myapp.vo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/oauth/callback")
@RequiredArgsConstructor
public class OAuth2Controller {

  @GetMapping("naver")
  @ResponseBody
  public String naverOAuthRedirect(@RequestParam String code, @RequestParam String state) {

    RestTemplate rt = new RestTemplate();

    HttpHeaders accessToken = new HttpHeaders();
    accessToken.add("Content-type", "application/x-www-form-urlencoded");

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("client_id", "{client_id}");
    params.add("client_secret", "{client_secret}");
    params.add("code" , code);
    params.add("grant_type", "authorization_code");
    params.add("state" , state);

    HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, accessToken);

    ResponseEntity<String> accessTokenResponse = rt.exchange(
        "https://nid.naver.com/oauth2.0/token",
        HttpMethod.POST,
        accessTokenRequest,
        String.class
    );

    return "accessToken: " + accessTokenResponse.getBody();
  }

  @GetMapping("google")
  @ResponseBody
  public String googleOAuthRedirect(@RequestParam String code, @RequestParam String state) {

    RestTemplate rt = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/x-www-form-urlencoded");

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("client_id", "{client_id}");
    params.add("client_secret", "{client_secret}");
    params.add("code", code);
    params.add("grant_type", "authorization_code");
    params.add("state" , state);

    HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

    ResponseEntity<String> accessTokenResponse = rt.exchange(
        "https://oauth2.googleapis.com/token",
        HttpMethod.POST,
        accessTokenRequest,
        String.class
    );
    return "accessToken: " + accessTokenResponse.getBody();
  }

  @GetMapping("kakao")
  @ResponseBody
  public String kakaoOAuthRedirect(@RequestParam String code, @RequestParam String state) {

    RestTemplate rt = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/x-www-form-urlencoded");

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("client_id", "{client_id}");
    params.add("client_secret", "{client_secret}");
    params.add("code", code);
    params.add("grant_type", "authorization_code");
    params.add("state" , state);

    HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(params, headers);

    ResponseEntity<String> accessTokenResponse = rt.exchange(
        "https://kauth.kakao.com/oauth/token",
        HttpMethod.POST,
        accessTokenRequest,
        String.class
    );
    return "accessToken: " + accessTokenResponse.getBody();
  }
}

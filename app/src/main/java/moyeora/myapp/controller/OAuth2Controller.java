package moyeora.myapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuth2Controller {

  @GetMapping("callback/naver")
  @ResponseBody
  public String callback(@RequestParam String code, @RequestParam String state) {

    RestTemplate rt = new RestTemplate();

    HttpHeaders accessToken = new HttpHeaders();
    accessToken.add("Content-type", "application/x-www-form-urlencoded");

    MultiValueMap<String, String> accessTokenParams = new LinkedMultiValueMap<>();
    accessTokenParams.add("grant_type", "authorization_code");
    accessTokenParams.add("client_id", "{client_id}");
    accessTokenParams.add("client_secret", "{client_secret}");
    accessTokenParams.add("code" , code);
    accessTokenParams.add("state" , state);

    HttpEntity<MultiValueMap<String, String>> accessTokenRequest = new HttpEntity<>(accessTokenParams, accessToken);

    ResponseEntity<String> accessTokenResponse = rt.exchange(
        "https://nid.naver.com/oauth2.0/token",
        HttpMethod.POST,
        accessTokenRequest,
        String.class
    );

    return "accessToken: " + accessTokenResponse.getBody();
  }
}

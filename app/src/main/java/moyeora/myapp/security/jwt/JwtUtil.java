//package moyeora.myapp.security.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//  @Value("${jwt.secretKey}")
//  private String secretKey;
//  @Value("${jwt.access.expiration}")
//  private Long expiredMs;
//
//  public String getUserName(String token) {
//    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
//        .getBody().get("username", String.class);
//  }
//
//  public boolean isExpired(String token) {
//    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
//        .getBody().getExpiration().before(new Date());
//  }
//
//  public String createJwt(String memberName, Long id) {
//    Claims claims = Jwts.claims();
//    claims.put("username", memberName);
//    claims.put("id", id);
//    System.out.println(secretKey);
//
//    String token = Jwts.builder()
//        .setClaims(claims)
//        .setIssuedAt(new Date(System.currentTimeMillis()))
//        .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
//        .signWith(SignatureAlgorithm.HS256, secretKey)
//        .compact();
//    return token;
//  }
//}

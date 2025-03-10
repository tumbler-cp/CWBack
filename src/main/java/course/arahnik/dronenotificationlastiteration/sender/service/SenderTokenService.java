package course.arahnik.dronenotificationlastiteration.sender.service;


import course.arahnik.dronenotificationlastiteration.sender.model.Sender;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class SenderTokenService {
  @Value("${subscription.sender.secret}")
  private String SECRET;

  public boolean isTokenValid(String token, Sender sender) {
    final Long id = extractSenderId(token);
    return sender.getId().equals(id) && !isTokenExpired(token);
  }

  public String generateToken(Sender sender) {
    return generateToken(new HashMap<>(), sender);
  }

  public String generateToken(Map<String, Object> claims, Sender sender) {
    return Jwts
            .builder()
            .claims(claims)
            .subject((sender).getId().toString())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
            .signWith(getSigningKey())
            .compact();
  }

  public Long extractSenderId(String token) {
    return Long.parseLong(extractClaim(token, Claims::getSubject));
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractClaims(String token) {
    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
  }

  private boolean isTokenExpired(String token) {
    return getExpirationDate(token).before(new Date());
  }

  private Date getExpirationDate(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private SecretKey getSigningKey() {
    byte[] bytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(bytes);
  }

}

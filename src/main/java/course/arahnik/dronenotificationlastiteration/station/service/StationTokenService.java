package course.arahnik.dronenotificationlastiteration.station.service;

import course.arahnik.dronenotificationlastiteration.station.model.DroneStation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class StationTokenService {
    @Value("${subscription.drone_station.secret}")
    private String SECRET;

    public boolean isTokenValid(String token, DroneStation station) {
        final UUID uuid = UUID.fromString(extractStationUUID(token));
        return station.getUuid()
            .equals(uuid);
    }

    public String generateToken(DroneStation station) {
        return generateToken(new HashMap<>(), station);
    }

    public String generateToken(Map<String, Object> claims, DroneStation station) {
        return Jwts
            .builder()
            .claims(claims)
            .subject((station).getUuid()
                .toString())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
            .signWith(getSigningKey())
            .compact();
    }

    public String extractStationUUID(String token) {
        return extractClaim(token, Claims::getSubject);
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

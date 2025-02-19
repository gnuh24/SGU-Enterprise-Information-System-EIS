package BackEnd.security;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Component
@Data
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretString;

    private SecretKey secretKey;  // Use a single secret key for both access and refresh tokens

    private static final long EXPIRATION_TIME_FOR_TOKEN = 30L * 24 * 60 * 60 * 1000;  // 30 days
    private static final long EXPIRATION_TIME_FOR_REFRESH_TOKEN = 30L * 24 * 60 * 60 * 1000;  // 30 days

    @PostConstruct
    public void init() {
        if (secretString == null || secretString.isBlank()) {
            throw new IllegalStateException("JWT secret key is not set in application.properties");
        }
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    // Generate Access Token
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_FOR_TOKEN))
                .signWith(secretKey)  // Use the same secret key for signing
                .compact();
    }

    // Generate Refresh Token
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_FOR_REFRESH_TOKEN))
                .signWith(secretKey)  // Use the same secret key for signing
                .compact();
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

    // Extract username from JWT Token without using library methods (manual extraction)
    public String getUsernameWithoutExpired(String token) {
        String[] parts = token.split("\\.");
        String encodedPayload = parts[1];
        String payload = new String(Base64.getUrlDecoder().decode(encodedPayload), StandardCharsets.UTF_8);
        return payload.split("\"")[3];
    }


}

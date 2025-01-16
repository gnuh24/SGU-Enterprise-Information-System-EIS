package BackEnd.Configure.WebSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
@Data
public class JWTUtils {

    private final SecretKey secretKeyForAccessToken ;
    private final SecretKey secretKeyForRefreshToken ;

    private  static  final long EXPIRATION_TIME_FOR_TOKEN =  30L * 24 * 60 * 60 * 1000; // 30 ngày
    private  static  final long EXPIRATION_TIME_FOR_REFRSH_TOKEN = 30L * 24 * 60 * 60 * 1000; // 30 ngày

    /**
     * CÁC BƯỚC MÃ HÓA ĐỂ TẠO RA 1 SECRECT KEY
     */

    public JWTUtils(){
        //Khởi tạo Secret key
        String secreteStringForAccess = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";
        byte[] keyBytes1 = Base64.getDecoder().decode(secreteStringForAccess.getBytes(StandardCharsets.UTF_8));
        this.secretKeyForAccessToken = new SecretKeySpec(keyBytes1, "HmacSHA256");

        //Khởi tạo Secret key
        String secreteStringForRefresh = "72437474474763T478378637664538745673865783678548735687R3";
        byte[] keyBytes2 = Base64.getDecoder().decode(secreteStringForRefresh.getBytes(StandardCharsets.UTF_8));
        this.secretKeyForRefreshToken = new SecretKeySpec(keyBytes2, "HmacSHA256");

    }

    //Tạo Token
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_FOR_TOKEN))
            .signWith(secretKeyForAccessToken)
            .compact();
    }

    // Tạo refresh Token
    public String generateRefreshToken(HashMap<String, Object> claims, UserDetails userDetails){
        return Jwts.builder()
            .claims(claims)
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_FOR_REFRSH_TOKEN))
            .signWith(secretKeyForRefreshToken)
            .compact();
    }


    // TODO: Các phương thức có sẵn

    //Tách email ra từ JWT Token
    public String extractUsernameAccessToken(String token){
        return extractClaims(true, token, Claims::getSubject);
    }

    //Tách email ra từ JWT Token
    public String extractUsernameRefreshToken(String token){
        return extractClaims(false, token, Claims::getSubject);
    }

    private <T> T extractClaims(Boolean isAccessToken, String token, Function<Claims, T> claimsTFunction){

        if (isAccessToken){
            return claimsTFunction.apply(
                Jwts.parser().verifyWith(secretKeyForAccessToken).build().parseSignedClaims(token).getPayload()
            );
        }

        return claimsTFunction.apply(
            Jwts.parser().verifyWith(secretKeyForRefreshToken).build().parseSignedClaims(token).getPayload()
        );

    }

    // TODO: Các phương thức Custom

    //Tách Email từ JWT Token (Dùng kỹ thuật xử lý chuỗi)
    public String extractUsernameWithoutLibrary(String token) {
        String[] parts = token.split("\\.");
        String encodedPayload = parts[1];
        String payload = new String(Base64.getUrlDecoder().decode(encodedPayload), StandardCharsets.UTF_8);
        return payload.split("\"")[3];
    }

    //Kiểm tra xem Token hợp lệ hay không (Không kiểm tra hạn dùng)
    public boolean isTokenValidWithoutExpired(String token, UserDetails userDetails){
        final String username = extractUsernameWithoutLibrary(token);
        return username.equals(userDetails.getUsername());
    }

    //Kiểm tra xem Token hợp lệ hay không
    public boolean isAccessTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsernameAccessToken(token);
        return (username.equals(userDetails.getUsername()) && !isAccessTokenExpired(token));
    }

    //Kiểm tra xem Token hợp lệ hay không
    public boolean isRefreshTokenValid(String token, UserDetails userDetails){
        final String username = extractUsernameRefreshToken(token);
        return (username.equals(userDetails.getUsername()) && !isRefreshTokenExpired(token));
    }

    //Kiểm tra xem Access Token hết hạn chưa ?
    public boolean isAccessTokenExpired(String token) {
        return extractClaims(true, token, Claims::getExpiration).before(new Date());
    }

    //Kiểm tra xem Refresh Token hết hạn chưa ?
    public boolean isRefreshTokenExpired(String token) {
        return extractClaims(false, token, Claims::getExpiration).before(new Date());
    }
}


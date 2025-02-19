package BackEnd.exceptions.JwtException;

import org.springframework.security.core.AuthenticationException;

public class UsernameNotFound extends AuthenticationException {
    public UsernameNotFound(String message) {
        super(message);
    }
}

package BackEnd.services;

import BackEnd.dto.response.auth.LoginInfoVO;
import BackEnd.dto.request.auth.LoginInputForm;
import org.apache.http.auth.InvalidCredentialsException;

public interface AuthService {
    LoginInfoVO signInForUser(LoginInputForm signinRequest) throws InvalidCredentialsException;
    LoginInfoVO signInForStaff(LoginInputForm signinRequest) throws InvalidCredentialsException;
}

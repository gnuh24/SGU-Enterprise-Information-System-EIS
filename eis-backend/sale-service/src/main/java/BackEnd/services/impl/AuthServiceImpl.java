package BackEnd.services.impl;

import BackEnd.security.JwtTokenProvider;
import BackEnd.entities.Account;
import BackEnd.dto.response.auth.LoginInfoVO;
import BackEnd.dto.request.auth.LoginInputForm;
import BackEnd.services.AccountService;
import BackEnd.services.AuthService;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private AuthExceptionHandler authExceptionHandler;

    @Override
    public LoginInfoVO signInForUser(LoginInputForm signinRequest) throws InvalidCredentialsException {
	LoginInfoVO response = new LoginInfoVO();

	Account user = accountService.getAccountByEmail(signinRequest.getEmail());

	if (user == null) {
	    throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng !!");
	}

//	if (!user.getType().equals(Account.AccountType.WEB)) {
//	    String message = "Email này đã được đăng ký thông qua " + user.getType() + ". Hãy đăng nhập bằng " + user.getType() + " ngay bên dưới!!";
//	    throw new InvalidCredentialsException(message);
//	}

	if (!user.getRole().toString().equals("USER")){
	    throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng !!");
	}

	if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
	    throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng !!");
	}

	if (user.getStatus().toString().equals("INACTIVE")){
	    throw new RuntimeException("Tài khoản của bạn chưa được kích hoạt hãy kiểm tra lại email " + signinRequest.getEmail());
	}

	if (user.getStatus().toString().equals("BANNED")){
	    throw new RuntimeException("Tài khoản của bạn đã bị khóa !! Nếu có vấn đề xin vui lòng liên hệ Admin");
	}

	response.setId(user.getId());
	response.setEmail(user.getUsername());
	response.setRole(user.getRole().toString());

	//Tạo Token
	String jwt = jwtTokenProvider.generateToken(user);
	response.setToken(jwt);
	response.setTokenExpirationTime("30 phút");

	//Tạo refresh token
	String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(), user);
	response.setRefreshToken(refreshToken);
	response.setRefreshTokenExpirationTime("7 ngày");

	return response;
    }

    @Override
    public LoginInfoVO signInForStaff(LoginInputForm signinRequest) throws InvalidCredentialsException {
	LoginInfoVO response = new LoginInfoVO();

	Account user = accountService.getAccountByEmail(signinRequest.getEmail());

	if (user == null) {
	    throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng !!");
	}

	if (user.getRole().toString().equals("USER")){
	    throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng !!");
	}

	if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
	    throw new InvalidCredentialsException("Email hoặc mật khẩu không đúng !!");
	}

	response.setId(user.getId());
	response.setEmail(user.getUsername());
	response.setRole(user.getRole().toString());

	//Tạo Token
	String jwt = jwtTokenProvider.generateToken(user);
	response.setToken(jwt);
	response.setTokenExpirationTime("30 phút");

	//Tạo refresh token
	String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(), user);
	response.setRefreshToken(refreshToken);
	response.setRefreshTokenExpirationTime("7 ngày");

	return response;
    }

//    @Override
//    public LoginInfoDTO refreshToken(String oldToken, String refreshToken) throws LoggedOutTokenException, MismatchedTokenAccountException, TokenExpiredException, InvalidJWTSignatureException{
//	LoginInfoDTO response = new LoginInfoDTO();
//
//	//Lấy Email từ Token (Dùng hàm viết tay -> Vì hàm có sẵn sẽ tự kiểm tra thời hạn của Token cũ)
//	String ourEmailByOldToken = jwtUtils.extractUsernameWithoutLibrary(oldToken);
//	String ourEmail = jwtUtils.extractUsernameWithoutLibrary(refreshToken);
//
//	if (!ourEmail.equals(ourEmailByOldToken)){
//	    throw new MismatchedTokenAccountException("Access Token và Refresh Token không cùng 1 chủ sở hữu !!");
//	}
//
//	//Tìm tài khoản dựa trên Email
//	Account account = accountService.getAccountByEmail(ourEmail);
//	try{
//	    if (jwtUtils.isRefreshTokenValid(refreshToken, account)) {
//		response.setStatusCode(200);
//
//		//Set Token mới
//		var jwt = jwtUtils.generateToken(account);
//		response.setToken(jwt);
//		response.setTokenExpirationTime("30 phút");
//
//		response.setMessage("Successfully Refreshed Token");
//	    }
//	}
//	catch (ExpiredJwtException e1) {
//	    throw new TokenExpiredException("Refresh Token của bạn đã hết hạn sử dụng !!");
//	}
//	catch (SignatureException e2){
//	    throw new InvalidJWTSignatureException("Refresh Token chứa signature không hợp lệ !!");
//	}
//
//	return response;
//    }
}

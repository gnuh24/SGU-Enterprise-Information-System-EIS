package BackEnd.controllers;


import BackEnd.apiresponse.ApiResponse;
import BackEnd.dto.request.account.AccountUpdateFormForEmail;
import BackEnd.dto.request.account.AccountUpdateFormForRole;
import BackEnd.dto.request.account.AccountUpdateFormForStatus;
import BackEnd.dto.response.account.AccountVOForProfile;
import BackEnd.entities.Account;
import BackEnd.dto.response.auth.LoginInfoVO;
import BackEnd.dto.request.auth.LoginInputForm;
import BackEnd.services.AccountService;
import BackEnd.services.AuthService;
import BackEnd.services.OTPService;
import jakarta.validation.Valid;
import org.apache.http.auth.InvalidCredentialsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/user/login")
    public ResponseEntity<ApiResponse<LoginInfoVO>> loginUser(@ModelAttribute @Valid LoginInputForm loginInputForm ) throws InvalidCredentialsException {
        LoginInfoVO loginInfoDTO = authService.signInForUser(loginInputForm);
        return ResponseEntity.ok(
                new ApiResponse<>(
                    200, // HTTP status code
                    "Login successful", // Success message
                    loginInfoDTO // Data returned from the service
                )
        );
    }

    @PostMapping("/staff/login")
    public ResponseEntity<ApiResponse<LoginInfoVO>> loginStaff(@ModelAttribute @Valid LoginInputForm loginInputForm ) throws InvalidCredentialsException {
        LoginInfoVO loginInfoDTO = authService.signInForStaff(loginInputForm);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200, // HTTP status code
                        "Login successful", // Success message
                        loginInfoDTO // Data returned from the service
                )
        );
    }



    @PostMapping("/send-otp-update-email")
    public ResponseEntity<ApiResponse<String>> sendOtpForUpdateEmail(@RequestHeader("Authorization") String jwtToken,
                                                                                                                        @ModelAttribute @Valid AccountUpdateFormForEmail form ) {

        String request = otpService.getOTPForUpdateEmail(jwtToken,  form.getNewEmail());
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200, // HTTP status code
                        request, // Success message
                        null
                )
        );
    }

    @PostMapping("/send-otp-reset-password")
    public ResponseEntity<ApiResponse<String>> sendOtpForResetPassword(@RequestHeader("Authorization") String jwtToken ) {

        String request = otpService.getOTPForResetPassword(jwtToken);
        return ResponseEntity.ok(
                new ApiResponse<>(
                        200, // HTTP status code
                        request, // Success message
                        null
                )
        );
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() {

//        JwtTokenProvider provider = new JwtTokenProvider();

//        String oldToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZpbHNraXBwZXJAZ21haWwuY29tIiwiaWF0IjoxNzI5MTY4NDg0LCJleHAiOjE3Mjk3NzMyODR9.rjn0l-7BkPdZ4lHZAZ5e44ezpcbdxXULxpXiE2gX3_Y";
//        String newToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkZXZpbHNraXBwZXJAZ21haWwuY29tIiwiaWF0IjoxNzM2ODc4NzAxLCJleHAiOjE3Mzk0NzA3MDF9.o5Wh5gWoxE3uF3fPrMRejA1zuO3LWOoCDAn0ykyF_xA";
//        System.err.println(provider.getUsernameWithoutExpired(newToken));
//        System.err.println(provider.getUsernameWithoutExpired(oldToken));
//        provider.decodeJWT(newToken);
//        System.err.println("___");
//        System.err.println(provider.getUsername(newToken));
//        System.err.println(provider.getUsername(oldToken));
        return ResponseEntity.ok("test");
    }

    @PatchMapping("/{id}/update-role")
    public ResponseEntity<ApiResponse<AccountVOForProfile>> updateRole(
            @PathVariable Integer id,
            @ModelAttribute @Valid AccountUpdateFormForRole form) {

        Account updatedAccount = accountService.updateRoleOfAccount(id, form.getRole());
        AccountVOForProfile result = modelMapper.map(updatedAccount, AccountVOForProfile.class);
        return ResponseEntity.ok(new ApiResponse<>(200, "Password updated successfully", result));
    }

    @PatchMapping("/{id}/update-status")
    public ResponseEntity<ApiResponse<AccountVOForProfile>> updateStatus(
            @PathVariable Integer id,
            @ModelAttribute @Valid AccountUpdateFormForStatus form) {

        Account updatedAccount = accountService.updateStatusOfAccount(id, form.getStatus());
        AccountVOForProfile result = modelMapper.map(updatedAccount, AccountVOForProfile.class);
        return ResponseEntity.ok(new ApiResponse<>(200, "Password updated successfully", result));
    }
}

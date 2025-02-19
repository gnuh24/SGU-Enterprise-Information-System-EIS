package BackEnd.services;


import BackEnd.entities.Account;
import BackEnd.entities.OTP;

public interface OTPService {

    String getOTPForUpdateEmail(String jwtToken, String newEmail);
    String getOTPForResetPassword(String jwtToken);

    boolean verifyOTPAndActivateAccount(String code);
    OTP createOTP(Account account, OTP.Category category, int length);

    OTP getOTPByCode(String code, OTP.Category category);
    void deleteOTP(OTP otp, OTP.Category category);
}

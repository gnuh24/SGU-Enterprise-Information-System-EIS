package BackEnd.services;

import BackEnd.entities.Account;
import BackEnd.entities.OTP;

public interface EmailService {

    void sendRegistrationUserConfirm(String email, OTP otp);
    void sendUpdatePasswordUserConfirm(Account account, OTP otp);
    void sendUpdateEmailUserConfirm(String newEmail, OTP otp);
    void sendResetPasswordUserConfirm(String email, OTP otp);

}

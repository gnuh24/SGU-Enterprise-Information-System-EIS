package BackEnd.controllers;

import BackEnd.apiresponse.ApiResponse;
import BackEnd.security.JwtTokenProvider;
import BackEnd.dto.request.account.*;
import BackEnd.dto.response.account.AccountVOForAdmin;
import BackEnd.dto.response.account.AccountVOForProfile;
import BackEnd.entities.Account;
import BackEnd.entities.AccountActivityLog;
import BackEnd.dto.request.account_activity_log.AccountActivityLogCreateForm;
import BackEnd.dto.response.account_actitivy_log.AccountActivityLogVO;
import BackEnd.dto.response.auth.LoginInfoVO;
import BackEnd.services.AccountActivityLogService;
import BackEnd.services.AccountService;
import BackEnd.services.OTPService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/accounts")
public class AccountController {

//    @Autowired
//    private JWTUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AccountActivityLogService accountActivityLogService;

    // Get Account by Email
    @GetMapping("/email")
    public ResponseEntity<ApiResponse<AccountVOForProfile>> getAccountByEmail(@RequestParam String email) {
        Account account = accountService.getAccountByEmail(email);
        AccountVOForProfile accountDTO = modelMapper.map(account, AccountVOForProfile.class);
        return ResponseEntity.ok(new ApiResponse<AccountVOForProfile>(200, "Account retrieved successfully", accountDTO));
    }

    // Get Account by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountVOForProfile>> getAccountById(@PathVariable Integer id) {
        Account account = accountService.getAccountById(id);
        AccountVOForProfile accountDTO = modelMapper.map(account, AccountVOForProfile.class);
        return ResponseEntity.ok(new ApiResponse<AccountVOForProfile>(200, "Account retrieved successfully", accountDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AccountVOForAdmin>>> getAllAccounts(Pageable pageable,
                                                                               AccountFilterForm filterForm) {

        // Get filtered, searched, and paginated accounts
        Page<Account> accounts = accountService.getAllAccounts(pageable, filterForm);

        // Map Account entities to DTOs
        Page<AccountVOForAdmin> accountDTOs = accounts.map(account -> modelMapper.map(account, AccountVOForAdmin.class));

        // Return the response
        return ResponseEntity.ok(
                new ApiResponse<Page<AccountVOForAdmin>>(200, "Account list retrieved successfully", accountDTOs)
        );

    }

    // Endpoint to check if email exists
    @GetMapping("/check-email")
    public ResponseEntity<ApiResponse<Boolean>> checkEmailExists(@RequestParam String email) {
        boolean exists = accountService.isEmailExists(email);

        ApiResponse<Boolean> response = new ApiResponse<>(200, "Email existence check completed successfully", exists);

        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<AccountVOForProfile>> createAccount(@ModelAttribute @Valid AccountCreateForm form) {
        // Create the account
        Account account = accountService.createAccount(form);

        // Map account entity to DTO
        AccountVOForProfile accountDTO = modelMapper.map(account, AccountVOForProfile.class);

        // Return response
        return ResponseEntity.ok(
                new ApiResponse<>(
                        201,
                        "Account created successfully. Please activate your account on your  email: " + account.getEmail() ,
                        accountDTO
                )
        );
    }

    @PostMapping("/activate-account")
    public ResponseEntity<ApiResponse<String>> activateAccount(@RequestParam String otp) {
        boolean isActivated = otpService.verifyOTPAndActivateAccount(otp);

        if (isActivated) {
            ApiResponse<String> response = new ApiResponse<>(200, "Account activated successfully.", null);
            return ResponseEntity.ok(response);
        }

        ApiResponse<String> response = new ApiResponse<>(400, "Invalid OTP or account.", null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountVOForProfile>> updateProfile(@PathVariable Integer id,
                                                                          @ModelAttribute @Valid AccountUpdateForm form) {
        AccountVOForProfile accountVOForProfile = modelMapper.map(accountService.updateAccount(id, form), AccountVOForProfile.class);
        return ResponseEntity.ok(new ApiResponse<AccountVOForProfile>(200, "Profile updated successfully", accountVOForProfile));
    }

    @PatchMapping("/{id}/update-password")
    public ResponseEntity<ApiResponse<AccountVOForProfile>> updatePassword(
                                @PathVariable Integer id,
                                @RequestHeader("Authorization") String jwtToken,
                                @ModelAttribute @Valid AccountUpdateFormForPassword form) {

            Account updatedAccount = accountService.updatePasswordOfAccount(jwtToken, form);
            AccountVOForProfile result = modelMapper.map(updatedAccount, AccountVOForProfile.class);
            return ResponseEntity.ok(new ApiResponse<>(200, "Password updated successfully", result));

    }

    @PatchMapping("/{id}/update-email")
    public ResponseEntity<ApiResponse<LoginInfoVO>> updateEmail(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String jwtToken,
            @ModelAttribute @Valid AccountUpdateFormForEmail form) {


        Account user = accountService.updateEmailOfAccount(jwtToken, form);
        LoginInfoVO response = modelMapper.map(user, LoginInfoVO.class);

        //Tạo Token
        String jwt = jwtTokenProvider.generateToken(user);
        response.setToken(jwt);
        response.setTokenExpirationTime("30 phút");

        //Tạo refresh token
        String refreshToken = jwtTokenProvider.generateRefreshToken(new HashMap<>(), user);
        response.setRefreshToken(refreshToken);
        response.setRefreshTokenExpirationTime("7 ngày");

        return ResponseEntity.ok(new ApiResponse<>(200, "Email updated successfully", response));

    }

    @PatchMapping("/{id}/reset-password")
    public ResponseEntity<ApiResponse<AccountVOForProfile>> resetPassword(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String jwtToken,
            @ModelAttribute @Valid AccountUpdateFormForResetPassword form) {

        Account updatedAccount = accountService.resetPasswordOfAccount(jwtToken, form);
        AccountVOForProfile result = modelMapper.map(updatedAccount, AccountVOForProfile.class);
        return ResponseEntity.ok(new ApiResponse<>(200, "Password updated successfully", result));
    }

    @PostMapping("/{accountId}/account-activity-logs")
    public ResponseEntity<ApiResponse<AccountActivityLogVO>> createAccountActivityLog(
            @PathVariable Integer accountId,
            @ModelAttribute AccountActivityLogCreateForm form) {
        // Create a new activity log
        AccountActivityLog newLog = accountActivityLogService.createAccountActivityLog(accountId, form.getActivityStatus());

        // Map entity to DTO
        AccountActivityLogVO dto = modelMapper.map(newLog, AccountActivityLogVO.class);
        dto.setAccountId(newLog.getAccount().getId()); // Map AccountId manually

        // Wrap DTO in ApiResponse
        ApiResponse<AccountActivityLogVO> response = new ApiResponse<>(201, "Activity log created successfully", dto);

        return ResponseEntity.ok(response);
    }

}

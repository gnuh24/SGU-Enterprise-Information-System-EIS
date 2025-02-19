package BackEnd.services.impl;


import BackEnd.exceptions.JwtException.TokenExpiredException;
import BackEnd.security.JwtTokenProvider;
import BackEnd.dto.request.account.*;
import BackEnd.entities.Account;
import BackEnd.entities.OTP;
import BackEnd.repositories.AccountRepository;
import BackEnd.services.EmailService;
import BackEnd.services.AccountService;
import BackEnd.services.OTPService;
import BackEnd.specifications.AccountSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OTPService otpService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Account with email " + username + " not found"));
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Account with email " + email + " not found"));
    }

    @Override
    public Account getAccountById(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Account with id " + id + " not found"));
    }

    @Override
    public Page<Account> getAllAccounts(Pageable pageable, AccountFilterForm filterForm) {
        Specification<Account> specification = AccountSpecification.buildWhere(filterForm);
        return accountRepository.findAll(specification, pageable);
    }

    @Override
//    @Cacheable(value = "account:email", key = "#email", unless = "#result == null")
    public boolean isEmailExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public Account createAccount(AccountCreateForm accountCreateForm) {
        Account account = modelMapper.map(accountCreateForm, Account.class);
        account.setPassword(passwordEncoder.encode(accountCreateForm.getPassword()));
        account = accountRepository.save(account);
        OTP otp = otpService.createOTP(account, OTP.Category.REGISTER, 25);
        emailService.sendRegistrationUserConfirm(account.getEmail(), otp);
        return account;
    }

    @Override
    public Account updateStatusOfAccount(Account account, Account.Status status) {
        account.setStatus(status);
        return accountRepository.save(account);
    }

    @Override
    public Account updateStatusOfAccount(Integer id, Account.Status status) {
        Account account = getAccountById(id);
        account.setStatus(status);
        return accountRepository.save(account);
    }

    @Override
    public Account updateRoleOfAccount(Integer accountId,  Account.Role role) {
        Account account = getAccountById(accountId);
        account.setRole(role);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Integer accountId, AccountUpdateForm accountUpdateForm) {
        Account existingAccount = getAccountById(accountId);

        if (accountUpdateForm.getFullname() != null) {
            existingAccount.setFullname(accountUpdateForm.getFullname());
        }
        if (accountUpdateForm.getBirthday() != null) {
            existingAccount.setBirthday(accountUpdateForm.getBirthday());
        }
        if (accountUpdateForm.getGender() != null) {
            existingAccount.setGender(accountUpdateForm.getGender());
        }
        if (accountUpdateForm.getAvatar() != null) {
            existingAccount.setAvatar(accountUpdateForm.getAvatar());
        }

        // Save updated account
        return accountRepository.save(existingAccount);
    }


    @Override
    public Account updatePasswordOfAccount(String jwtToken, AccountUpdateFormForPassword form) throws RuntimeException {

        String email = jwtTokenProvider.getUsernameWithoutExpired(jwtToken);
        Account account = getAccountByEmail(email);

        if (!passwordEncoder.matches(form.getOldPassword(), account.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không đúng !!");
        }

        String newPassword = passwordEncoder.encode(form.getNewPassword());
        account.setPassword(newPassword);
        return accountRepository.save(account);
    }

    @Override
    public Account updateEmailOfAccount(String jwtToken, AccountUpdateFormForEmail form) throws RuntimeException {

        String otpCode = form.getOtp();

        OTP otp = otpService.getOTPByCode(otpCode, OTP.Category.UPDATE_EMAIL);

        String oldEmail = jwtTokenProvider.getUsernameWithoutExpired(jwtToken);

        if (!oldEmail.equals(otp.getAccount().getEmail())) {
            throw new RuntimeException("Token bạn gửi không có chức năng thay đổi email của tài khoản này !!");
        }

        if (otp.getExpirationTime().isAfter(LocalDateTime.now())) {
            Account account = getAccountByEmail(oldEmail);
            account.setEmail(form.getNewEmail());
            accountRepository.save(account);
            return account;
        } else {
            // remove Registration User Token
            otpService.deleteOTP(otp, OTP.Category.UPDATE_EMAIL);
            throw new TokenExpiredException("OTP kích hoạt tài khoản của bạn đã hết hạn !!");

        }

    }

    @Override
    public Account resetPasswordOfAccount(String jwtToken, AccountUpdateFormForResetPassword form) throws RuntimeException {
        String email = jwtTokenProvider.getUsernameWithoutExpired(jwtToken);
        Account account = getAccountByEmail(email);

        OTP otp = otpService.getOTPByCode(form.getOtp(), OTP.Category.RESET_PASSWORD);
        if (!email.equals(otp.getAccount().getEmail())){
            throw new RuntimeException("Token bạn gửi không có chức năng thay đổi email của tài khoản này !!");
        }


        if (otp.getExpirationTime().isAfter(LocalDateTime.now())) {
            String newPassword = passwordEncoder.encode(form.getNewPassword());
            account.setPassword(newPassword);
            return accountRepository.save(account);
        } else {
            // remove Registration User Token
            otpService.deleteOTP(otp, OTP.Category.RESET_PASSWORD);
            throw new TokenExpiredException("OTP kích hoạt tài khoản của bạn đã hết hạn !!");

        }
    }
}


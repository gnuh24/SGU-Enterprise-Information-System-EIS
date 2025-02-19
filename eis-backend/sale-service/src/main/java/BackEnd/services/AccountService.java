package BackEnd.services;

import BackEnd.dto.request.account.*;
import BackEnd.entities.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AccountService extends UserDetailsService {
    Account getAccountByEmail(String email);

    Account getAccountById(Integer id);

    Page<Account> getAllAccounts(Pageable pageable, AccountFilterForm filterForm);

    boolean isEmailExists(String email);

    Account createAccount(AccountCreateForm accountCreateForm);

    Account updateAccount(Integer accountId, AccountUpdateForm accountUpdateForm);

    Account updateStatusOfAccount(Account account, Account.Status status);

    Account updateStatusOfAccount(Integer id, Account.Status status);

    Account updateRoleOfAccount(Integer accountId,  Account.Role role);

    Account updatePasswordOfAccount(String jwtToken, AccountUpdateFormForPassword form) throws RuntimeException;

    Account updateEmailOfAccount(String jwtToken, AccountUpdateFormForEmail form) throws RuntimeException;

    Account resetPasswordOfAccount(String jwtToken, AccountUpdateFormForResetPassword form) throws RuntimeException;


}
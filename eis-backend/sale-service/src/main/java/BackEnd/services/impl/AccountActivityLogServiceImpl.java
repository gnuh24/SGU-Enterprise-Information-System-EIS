package BackEnd.services.impl;


import BackEnd.entities.Account;
import BackEnd.entities.AccountActivityLog;
import BackEnd.repositories.AccountActivityLogRepository;
import BackEnd.services.AccountService;
import BackEnd.services.AccountActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountActivityLogServiceImpl implements AccountActivityLogService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountActivityLogRepository accountActivityLogRepository;

    @Override
    public List<AccountActivityLog> findByAccountId(Integer accountId) {
	return accountActivityLogRepository.findByAccountId(accountId);
    }

    @Override
    public List<AccountActivityLog> findByAccountIdAndActivityStatus(Integer accountId, AccountActivityLog.ActivityStatus activityStatus) {
	return accountActivityLogRepository.findByAccountIdAndActivityStatus(accountId, activityStatus);
    }

    @Override
    public AccountActivityLog findFirstByAccountIdOrderByActivityTimeDesc(Integer accountId) {
	return accountActivityLogRepository.findFirstByAccountIdOrderByActivityTimeDesc(accountId);
    }

    @Override
    public AccountActivityLog createAccountActivityLog(Integer accountId, AccountActivityLog.ActivityStatus status) {

	Account account = accountService.getAccountById(accountId);
	// Create a new activity log
	AccountActivityLog activityLog = new AccountActivityLog();
	activityLog.setAccount(account);
	activityLog.setActivityStatus(status);
	activityLog.setActivityTime(LocalDateTime.now());

	// Save to the repository
	return accountActivityLogRepository.save(activityLog);
    }
}

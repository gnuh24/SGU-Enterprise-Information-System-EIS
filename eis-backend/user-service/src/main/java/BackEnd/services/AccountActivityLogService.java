package BackEnd.services;


import BackEnd.entities.AccountActivityLog;

import java.util.List;

public interface AccountActivityLogService {
    // Find all activity logs for a specific account by AccountId
    List<AccountActivityLog> findByAccountId(Integer accountId);

    // Find all activity logs for a specific account and status
    List<AccountActivityLog> findByAccountIdAndActivityStatus(Integer accountId, AccountActivityLog.ActivityStatus activityStatus);

    // Find the most recent activity log for an account
    AccountActivityLog findFirstByAccountIdOrderByActivityTimeDesc(Integer accountId);

    // Create a new activity log for an account
    AccountActivityLog createAccountActivityLog(Integer accountId, AccountActivityLog.ActivityStatus status);
}


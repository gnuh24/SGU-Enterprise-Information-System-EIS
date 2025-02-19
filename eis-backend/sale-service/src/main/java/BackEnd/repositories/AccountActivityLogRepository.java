package BackEnd.repositories;

import BackEnd.entities.AccountActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountActivityLogRepository extends JpaRepository<AccountActivityLog, Integer> {

    // Find all activity logs for a specific account by AccountId
    List<AccountActivityLog> findByAccountId(Integer accountId);

    // Find all activity logs for a specific account and status
    List<AccountActivityLog> findByAccountIdAndActivityStatus(Integer accountId, AccountActivityLog.ActivityStatus activityStatus);

    // Find the most recent activity log for an account
    AccountActivityLog findFirstByAccountIdOrderByActivityTimeDesc(Integer accountId);
}

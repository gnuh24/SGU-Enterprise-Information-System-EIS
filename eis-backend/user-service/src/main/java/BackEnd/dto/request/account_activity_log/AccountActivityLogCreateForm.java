package BackEnd.dto.request.account_activity_log;

import BackEnd.entities.AccountActivityLog;
import lombok.Data;

@Data
public class AccountActivityLogCreateForm {
    private AccountActivityLog.ActivityStatus activityStatus;
}

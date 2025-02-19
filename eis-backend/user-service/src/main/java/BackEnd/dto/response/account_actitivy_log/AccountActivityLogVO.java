package BackEnd.dto.response.account_actitivy_log;

import BackEnd.entities.AccountActivityLog.ActivityStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountActivityLogVO {
    private Integer id;
    private ActivityStatus activityStatus;
    private LocalDateTime activityTime;
    private Integer accountId; // Assuming AccountId is needed in the response
}

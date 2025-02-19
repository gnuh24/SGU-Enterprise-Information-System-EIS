package BackEnd.dto.request.account;

import BackEnd.entities.Account;
import lombok.Data;

@Data
public class AccountUpdateFormForStatus {

    private Account.Status status;

}

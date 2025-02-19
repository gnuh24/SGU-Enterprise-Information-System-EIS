package BackEnd.dto.request.account;

import BackEnd.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateFormForRole {

    private Account.Role role;
}

package BackEnd.Form.UsersForms.AccountForms;

import BackEnd.Entity.AccountEntity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateFormForRole {

    private Integer accountId;

    private Account.Role role;
}

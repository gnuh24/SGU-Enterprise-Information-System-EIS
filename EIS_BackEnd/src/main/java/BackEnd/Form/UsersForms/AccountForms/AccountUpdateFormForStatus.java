package BackEnd.Form.UsersForms.AccountForms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateFormForStatus {

    private Integer accountId;

    private Boolean status;
}

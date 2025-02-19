package BackEnd.dto.response.account;


import BackEnd.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountVOForAdmin {

    private Integer id;

    private String email;

    private String fullname;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Account.Status status;

    private Account.Role role;

}

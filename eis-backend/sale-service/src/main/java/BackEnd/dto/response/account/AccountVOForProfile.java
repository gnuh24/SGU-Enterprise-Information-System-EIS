package BackEnd.dto.response.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountVOForProfile {

    private Integer id;

    private String email;

    private String gender;

    private String fullname;

    private LocalDate birthday;

    private String avatar;

//    private String newToken;
//
//    private String newRefreshToken;

}

package BackEnd.dto.request.account;

import BackEnd.entities.Account;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccountUpdateForm {

    private String avatar;

//    @Pattern(regexp = "Male|Female|Other", message = "Giới tính phải là 'Male', 'Female', hoặc 'Other' !!")
    private Account.Gender gender;

    @Size(max = 255, message = "Họ tên không được dài quá 255 ký tự !!")
    private String fullname;

    private LocalDate birthday;

}

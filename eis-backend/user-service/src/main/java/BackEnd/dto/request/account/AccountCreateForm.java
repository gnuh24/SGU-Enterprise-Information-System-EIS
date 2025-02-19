package BackEnd.dto.request.account;

import BackEnd.entities.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class AccountCreateForm {

    @NotBlank(message = "Email không được để trống !!")
    @Email(message = "Email phải đúng định dạng !!")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống !!")
    @Size(min = 6, max = 20, message = "Mật khẩu phải có độ dài từ 6 đến 20 ký tự !!")
    private String password;

    private Account.Gender gender;

    @NotBlank(message = "Họ và tên không được để trống !!")
    @Size(max = 50, message = "Họ và tên không được vượt quá 50 ký tự !!")
    private String fullname;

    @NotNull(message = "Ngày sinh không được để trống !!")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ !!")
    private LocalDate birthday;
}

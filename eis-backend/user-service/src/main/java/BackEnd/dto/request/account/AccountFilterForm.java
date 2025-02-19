package BackEnd.dto.request.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountFilterForm {

//    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    private Date from;
//
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
//    private Date to;

    private String search;

    private String role;

    private String status;

}


package BackEnd.Form.ShoppingForms.OrderForm;


import BackEnd.Entity.ShoppingEntities.Order;
import BackEnd.Form.ShoppingForms.OrderDetailForm.OrderDetailDTO;
import BackEnd.Form.ShoppingForms.OrderStatusForms.OrderStatusDTO;
import BackEnd.Form.UsersForms.UserInformationForms.UserInformationDTOForOrder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTODetailAdmin {

    private String id;

    private Integer totalPrice;
    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime orderTime;

    private String note;

    private Order.PaymentMethod payment;

    @JsonProperty("userInformation")
    private UserInformationDTOForOrder accountUserInformation;

    private List<OrderStatusDTO> orderStatuses;

    private List<OrderDetailDTO> orderDetails;


}


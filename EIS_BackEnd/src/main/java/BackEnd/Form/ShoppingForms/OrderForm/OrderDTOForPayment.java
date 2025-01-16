package BackEnd.Form.ShoppingForms.OrderForm;

import BackEnd.Entity.ShoppingEntities.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTOForPayment {

    private String id;

    private Integer totalPrice;

    @JsonFormat(pattern = "HH:mm:ss dd/MM/yyyy")
    private LocalDateTime orderTime;

    private Order.PaymentMethod payment;

    private String url;


}

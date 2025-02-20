package com.sgu.sale.dto.response.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryPublicDTO {


    private  Integer id;

    private String productName;

    private Integer price;

    private String image;


}

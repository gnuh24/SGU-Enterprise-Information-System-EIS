package com.sgu.sale.dto.request.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterFormRequest {

    private Boolean status;

    private  Integer brandId;

    private  Integer categoryId;

}

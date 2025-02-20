package com.sgu.sale.dto.response.product;

import com.sgu.sale.dto.response.brand.BrandInProductDTO;
import com.sgu.sale.dto.response.category.CategoryInProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryManagementDTO {

    private  Integer id;

    private String productName;

    private Boolean status;

    private Integer quantity;

    private Integer price;

    private String image;

    private String createTime;

    private BrandInProductDTO brand;

    private CategoryInProductDTO category;

}

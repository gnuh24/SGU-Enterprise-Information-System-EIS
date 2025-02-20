package com.sgu.sale.dto.response.product;

import com.sgu.sale.dto.response.brand.BrandInProductDTO;
import com.sgu.sale.dto.response.category.CategoryInProductDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailPublicDTO {

    private Integer id;

    private String productName;

    private Integer price;

    private String image;


    private String description;

    private String origin;

    private Integer capacity;

    private Integer abv;

    private Integer quantity;


    private BrandInProductDTO brand;

    private CategoryInProductDTO category;
}

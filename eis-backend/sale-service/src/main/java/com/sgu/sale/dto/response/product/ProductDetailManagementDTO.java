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
public class ProductDetailManagementDTO {

    private Integer id;

    private String productName;

    private Boolean status;

    private String createTime;

    private String image; // Assuming you are storing the image URL or path

    private String description;

    private String origin;

    private Integer capacity;

    private Integer abv;

//    private List<BatchDTO> batches;

    private BrandInProductDTO brand;

    private CategoryInProductDTO category;

}

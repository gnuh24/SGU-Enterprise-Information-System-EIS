package com.sgu.sale.dto.request.brand;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BrandCreateFormRequest {

    @NotBlank(message = "Bạn không được để trống tên thương hiệu !!")
    @Size(min = 3, max = 100, message = "Tên thương hiệu phải từ 3 đến 100 ký tự !!")
    private String brandName;

}

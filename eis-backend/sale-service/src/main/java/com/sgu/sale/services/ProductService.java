package com.sgu.sale.services;

import com.sgu.sale.dto.request.product.ProductFilterFormRequest;
import com.sgu.sale.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ProductService {

    Page<Product> getAllProduct(Pageable pageable, String search, ProductFilterFormRequest form);

    Product getProductById(Integer productId);

    int updateDefaultBrandOfProduct(Integer brandId);

    int updateDefaultCategoryOfProduct(Integer categoryId);
//
//    Product createProduct(ProductCreateForm form);
//
//    Product updateProduct(ProductUpdateForm form);

}
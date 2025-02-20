package com.sgu.sale.services;

import com.sgu.sale.dto.request.brand.BrandCreateFormRequest;
import com.sgu.sale.dto.request.brand.BrandUpdateFormRequest;
import com.sgu.sale.entities.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {

    List<Brand> getAllBrandNoPaging();

    Page<Brand> getAllBrand(Pageable pageable, String search);

    Brand getBrandById(Integer id);

    Brand createBrand(BrandCreateFormRequest form)  throws Exception ;

    Brand updateBrand(Integer id, BrandUpdateFormRequest form)  throws Exception ;

//    void deleteBrand(Integer brandId);

}

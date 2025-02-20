package com.sgu.sale.services.impl;

import com.sgu.sale.dto.request.brand.BrandCreateFormRequest;
import com.sgu.sale.dto.request.brand.BrandUpdateFormRequest;
import com.sgu.sale.entities.Brand;
import com.sgu.sale.repositories.BrandRepository;
import com.sgu.sale.services.BrandService;
import com.sgu.sale.services.ProductService;
import com.sgu.sale.specifications.BrandSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    @Lazy
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Brand> getAllBrandNoPaging() {
	return brandRepository.findAll();
    }

    @Override
    public Page<Brand> getAllBrand(Pageable pageable, String search) {
	Specification<Brand> specification = BrandSpecification.buildWhere(search);
	return brandRepository.findAll(specification, pageable);
    }

    @Override
    public Brand getBrandById(Integer id){
	return brandRepository.findById(id)
		.orElseThrow(() -> new EntityNotFoundException("ID: " + id + " không tồn tại!"));
    }

    @Override
    @Transactional
    public Brand createBrand(BrandCreateFormRequest form) throws Exception {
	validateBrandName(form.getBrandName(), null);

	Brand entity = modelMapper.map(form, Brand.class);
	return brandRepository.save(entity);
    }

    @Override
    @Transactional
    public Brand updateBrand(Integer id, BrandUpdateFormRequest form) throws Exception {
	Brand oldBrand = brandRepository.findById(id)
		.orElseThrow(() -> new Exception("ID: " + id + " không tồn tại!"));

	validateBrandName(form.getBrandName(), oldBrand.getId());

	Brand updatedBrand = modelMapper.map(form, Brand.class);
	return brandRepository.save(updatedBrand);
    }

//    @Override
//    @Transactional
//    public void deleteBrand(Integer brandId) {
//	Brand oldBrand = brandRepository.findById(brandId)
//		.orElseThrow(() -> new EntityNotFoundException("ID: " + brandId + " không tồn tại!"));
//
//	productService.updateDefaultBrandOfProduct(brandId);
//	brandRepository.deleteById(brandId);
//    }

    /**
     * Validates if the brand name already exists.
     * If an ID is provided, it ensures the name is not used by another brand.
     *
     * @param brandName the brand name to validate
     * @param currentId the ID of the current brand (for updates)
     * @throws Exception if the brand name is invalid
     */
    private void validateBrandName(String brandName, Integer currentId) throws Exception {
	Optional<Brand> existingBrand = brandRepository.findByBrandName(brandName);

	if (existingBrand.isPresent() && !existingBrand.get().getId().equals(currentId)) {
	    throw new Exception("Tên thương hiệu '" + brandName + "' đã tồn tại, vui lòng chọn tên khác!");
	}
    }
}


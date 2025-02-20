package com.sgu.sale.services.impl;

import com.sgu.sale.dto.request.product.ProductFilterFormRequest;
import com.sgu.sale.entities.Brand;
import com.sgu.sale.entities.Category;
import com.sgu.sale.entities.Product;
import com.sgu.sale.external.CloundinaryServices;
import com.sgu.sale.repositories.ProductRepository;
import com.sgu.sale.services.BrandService;
import com.sgu.sale.services.CategoryService;
import com.sgu.sale.services.ProductService;
import com.sgu.sale.specifications.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class ProductServiceImpl implements ProductService {

//public class ProductServiceImpl{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

//    @Autowired
//    @Lazy
//    private BatchService batchService;

    @Override
    public Page<Product> getAllProduct(Pageable pageable, String search, ProductFilterFormRequest form) {
	Specification<Product> where = ProductSpecification.buildWhere(search, form);
	return productRepository.findAll(where, pageable);
    }

    @Override
    public Product getProductById(Integer productId) {
	return productRepository.findById(productId).orElse(null);
    }


    @Override
    public int updateDefaultBrandOfProduct(Integer brandId) {
	return productRepository.updateBrandToDefault(brandId);
    }

    @Override
    public int updateDefaultCategoryOfProduct(Integer categoryId) {
	return productRepository.updateCategoryToDefault(categoryId);
    }

//    @Override
//    @Transactional
//    public Product createProduct(ProductCreateFormRe form){
//
//	Product entity = new Product();
//
//	entity.setProductName(form.getProductName());
//	entity.setAbv(form.getAbv());
//	entity.setOrigin(form.getOrigin());
//	entity.setCapacity(form.getCapacity());
//	entity.setDescription(form.getDescription());
//
//	Brand brand = brandService.getBrandById(form.getBrandId());
//	entity.setBrand(brand);
//
//	Category category = categoryService.getCategoryById(form.getCategoryId());
//	entity.setCategory(category);
//
//	entity.setImage(CloundinaryServices.createImageFromMultipart(form.getImage()));
//
//	entity = productRepository.save(entity);
//
//	BatchCreateForm batchCreateForm = new BatchCreateForm();
//	batchCreateForm.setUnitPrice(0);
//	batchCreateForm.setProductId(entity.getId());
//	batchCreateForm.setQuantity(0);
//	batchCreateForm.setMaxQuantity(0);
//
//	batchService.createBatch(batchCreateForm);
//
//	return entity;
//    }
//
//    @Override
//    @Transactional
//    public Product updateProduct(ProductUpdateForm form) {
//	// Fetch the existing product from the repository
//	Product oldProduct = getProductById(form.getId());
//
//	// Update fields if they are not null
//
//
//	if (form.getStatus() != null) {
//	    oldProduct.setStatus(form.getStatus());
//	}
//	if (form.getImage() != null) {
//	    if (oldProduct.getImage() != null){
//		CloundinaryServices.deleteImage(oldProduct.getImage());
//	    }
//	    oldProduct.setImage(
//		    CloundinaryServices.createImageFromMultipart(form.getImage())
//	    );
//	}
//
//	if (form.getOrigin() != null) {
//	    oldProduct.setOrigin(form.getOrigin());
//	}
//	if (form.getCapacity() != null) {
//	    oldProduct.setCapacity(form.getCapacity());
//	}
//	if (form.getAbv() != null) {
//	    oldProduct.setAbv(form.getAbv());
//	}
//	if (form.getDescription() != null) {
////            if (form.getDescription().equals("")){
////                oldProduct.setDescription("Không có mô tả nào cho sản phẩm trên");
////            }
//	    oldProduct.setDescription(form.getDescription());
//	}
//	if (form.getBrandId() != null) {
//	    Brand newBrand = brandService.getBrandById(form.getBrandId());
//	    oldProduct.setBrand(newBrand);
//	}
//	if (form.getCategoryId() != null) {
//	    Category newCategory = categoryService.getCategoryById(form.getCategoryId());
//	    oldProduct.setCategory(newCategory);
//	}
//
//	// Save the updated product
//	return productRepository.save(oldProduct);
//    }
//

}


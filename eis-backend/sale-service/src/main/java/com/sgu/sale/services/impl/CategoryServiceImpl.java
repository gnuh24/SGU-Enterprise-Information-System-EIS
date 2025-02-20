package com.sgu.sale.services.impl;

import com.sgu.sale.dto.request.category.CategoryCreateFormRequest;
import com.sgu.sale.dto.request.category.CategoryUpdateFormRequest;
import com.sgu.sale.entities.Category;
import com.sgu.sale.repositories.CategoryRepository;
import com.sgu.sale.services.CategoryService;
import com.sgu.sale.services.ProductService;
import com.sgu.sale.specifications.CategorySpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    @Lazy
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public List<Category> getAllCategoryNoPaging() {
	return categoryRepository.findAll();
    }

    @Override
    public Page<Category> getAllCategory(Pageable pageable, String search) {
	Specification<Category> specification = CategorySpecification.buildWhere(search);
	return categoryRepository.findAll(specification, pageable);
    }

    @Override
    public Category getCategoryById(Integer id) {
	return categoryRepository.findById( id ).orElse(null);
    }

    @Override
    @Transactional
    public Category createCategory(CategoryCreateFormRequest form) throws Exception {

	if (categoryRepository.existsByCategoryName(form.getCategoryName())){
	    throw new Exception(form.getCategoryName() + " đã tồn tại, xin vui lòng chọn tên khác !!" );
	}

	Category entity = modelMapper.map(form, Category.class);
	return categoryRepository.save(entity);
    }

    @Override
    @Transactional
    public Category updateCategory(Integer id, CategoryUpdateFormRequest form) throws Exception {

	Category oldEntity = categoryRepository.findById(id).orElse(null) ;
	if (oldEntity == null){
	    throw new Exception("ID: " + id + " không tồn tại !!" );
	}

	if (categoryRepository.existsByCategoryName(form.getCategoryName())){
	    throw new Exception(form.getCategoryName() + " đã tồn tại, xin vui lòng chọn tên khác !!" );
	}

	oldEntity.setCategoryName(form.getCategoryName());
	return categoryRepository.save(oldEntity);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer categoryId) throws Exception {
	Category oldEntity = categoryRepository.findById(categoryId).orElse(null) ;
	if (oldEntity == null){
	    throw new Exception("ID: " +  categoryId + " không tồn tại !!" );
	}

	productService.updateDefaultCategoryOfProduct(categoryId);
	categoryRepository.deleteById(categoryId);
    }

}


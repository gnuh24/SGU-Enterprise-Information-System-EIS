package com.sgu.sale.services;

import com.sgu.sale.dto.request.category.CategoryCreateFormRequest;
import com.sgu.sale.dto.request.category.CategoryUpdateFormRequest;
import com.sgu.sale.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategoryNoPaging();

    Page<Category> getAllCategory(Pageable pageable, String search);

    Category getCategoryById(Integer id);

    Category createCategory(CategoryCreateFormRequest form) throws Exception;

    Category updateCategory(Integer id, CategoryUpdateFormRequest form) throws Exception;

    void deleteCategory(Integer categoryId) throws Exception;
}

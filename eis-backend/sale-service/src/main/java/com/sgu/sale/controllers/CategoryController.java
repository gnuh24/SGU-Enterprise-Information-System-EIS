package com.sgu.sale.controllers;

import com.sgu.sale.apiresponse.ApiResponse;
import com.sgu.sale.dto.request.category.CategoryCreateFormRequest;
import com.sgu.sale.dto.request.category.CategoryUpdateFormRequest;
import com.sgu.sale.dto.response.category.CategoryResponseDTO;
import com.sgu.sale.entities.Category;
import com.sgu.sale.services.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/category")
@CrossOrigin(origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/no-paging")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getAllCategoryNoPaging() {
	List<Category> list = categoryService.getAllCategoryNoPaging();
	List<CategoryResponseDTO> dtos = modelMapper.map(list, new TypeToken<List<CategoryResponseDTO>>(){}.getType());

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dtos));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<CategoryResponseDTO>>> getAllCategory(Pageable pageable,
										 @RequestParam(name = "search", required = false) String search) {
	Page<Category> entities = categoryService.getAllCategory(pageable, search);
	List<CategoryResponseDTO> dtos = modelMapper.map(entities.getContent(), new TypeToken<List<CategoryResponseDTO>>(){}.getType());
	Page<CategoryResponseDTO> dtoPage = new PageImpl<>(dtos, pageable, entities.getTotalElements());

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dtoPage));
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(@PathVariable Integer categoryId) {
	Category entity = categoryService.getCategoryById(categoryId);
	CategoryResponseDTO dto = modelMapper.map(entity, CategoryResponseDTO.class);

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dto));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(@ModelAttribute @Valid CategoryCreateFormRequest form) throws Exception {
	Category entity = categoryService.createCategory(form);
	CategoryResponseDTO dto = modelMapper.map(entity, CategoryResponseDTO.class);

	return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Category created successfully", dto));
    }

    @PatchMapping(value = "/{categoryId}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(@PathVariable Integer categoryId,
	    																@ModelAttribute @Valid CategoryUpdateFormRequest form) throws Exception {
	Category entity = categoryService.updateCategory(categoryId, form);
	CategoryResponseDTO dto = modelMapper.map(entity, CategoryResponseDTO.class);

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Category updated successfully", dto));
    }

    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Integer categoryId) throws Exception {
	categoryService.deleteCategory(categoryId);

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Category deleted successfully", null));
    }


}

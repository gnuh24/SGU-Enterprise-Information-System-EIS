package com.sgu.sale.controllers;

import com.sgu.sale.apiresponse.ApiResponse;
import com.sgu.sale.dto.request.brand.BrandCreateFormRequest;
import com.sgu.sale.dto.request.brand.BrandUpdateFormRequest;
import com.sgu.sale.dto.response.brand.BrandResponseDTO;
import com.sgu.sale.entities.Brand;
import com.sgu.sale.services.BrandService;
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
@RequestMapping(value = "/brand")
@CrossOrigin(origins = "*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/no-paging")
    public ResponseEntity<ApiResponse<List<BrandResponseDTO>>> getAllBrandNoPaging() {
	List<Brand> list = brandService.getAllBrandNoPaging();
	List<BrandResponseDTO> dtos = modelMapper.map(list, new TypeToken<List<BrandResponseDTO>>() {}.getType());

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dtos));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<BrandResponseDTO>>> getAllBrand(Pageable pageable,
									   @RequestParam(name = "search", required = false) String search) {
	Page<Brand> entities = brandService.getAllBrand(pageable, search);
	List<BrandResponseDTO> dtos = modelMapper.map(entities.getContent(), new TypeToken<List<BrandResponseDTO>>() {}.getType());
	Page<BrandResponseDTO> dtoPage = new PageImpl<>(dtos, pageable, entities.getTotalElements());

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dtoPage));
    }

    @GetMapping(value = "/{brandId}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> getBrandById(@PathVariable Integer brandId) {
	Brand entity = brandService.getBrandById(brandId);
	BrandResponseDTO dto = modelMapper.map(entity, BrandResponseDTO.class);

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dto));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<BrandResponseDTO>> createBrand(@ModelAttribute @Valid BrandCreateFormRequest form) throws Exception {
	Brand entity = brandService.createBrand(form);
	BrandResponseDTO dto = modelMapper.map(entity, BrandResponseDTO.class);

	return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Brand created successfully", dto));
    }

    @PatchMapping(value = "/{brandId}")
    public ResponseEntity<ApiResponse<BrandResponseDTO>> updateBrand(@PathVariable Integer brandId,
								     @ModelAttribute @Valid BrandUpdateFormRequest form) throws Exception {
	Brand entity = brandService.updateBrand(brandId, form);
	BrandResponseDTO dto = modelMapper.map(entity, BrandResponseDTO.class);

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Brand updated successfully", dto));
    }

    @DeleteMapping(value = "/{brandId}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Integer brandId) throws Exception {
	brandService.deleteBrand(brandId);

	return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Brand deleted successfully", null));
    }
}


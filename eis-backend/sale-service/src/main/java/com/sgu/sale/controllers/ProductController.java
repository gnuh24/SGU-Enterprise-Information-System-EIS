package com.sgu.sale.controllers;

import com.sgu.sale.apiresponse.ApiResponse;
import com.sgu.sale.dto.request.product.ProductFilterFormRequest;
import com.sgu.sale.dto.response.product.ProductSummaryManagementDTO;
import com.sgu.sale.dto.response.product.ProductDetailManagementDTO;
import com.sgu.sale.entities.Product;
import com.sgu.sale.services.ProductService;
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

        import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/management")
    public ResponseEntity<ApiResponse<Page<ProductSummaryManagementDTO>>> getAllProductForManagement(Pageable pageable,
                                                                                                     @RequestParam(name = "search", required = false) String search,
                                                                                                     ProductFilterFormRequest form) {
        Page<Product> entities = productService.getAllProduct(pageable, search, form);
        List<ProductSummaryManagementDTO> dtos = modelMapper.map(entities.getContent(), new TypeToken<List<ProductSummaryManagementDTO>>() {}.getType());
        Page<ProductSummaryManagementDTO> dtoPage = new PageImpl<>(dtos, pageable, entities.getTotalElements());

//        for (ProductDTOListAdmin dtoListAdmin: dtos){
//	    Batch batch = batchService.getTheValidBatch(dtoListAdmin.getId());
//
//	    if (batch == null){
//		batch = batchService.getTheValidBatchBackup(dtoListAdmin.getId());
//	    }
//
//	    dtoListAdmin.setPrice(batch.getUnitPrice());
//	    dtoListAdmin.setQuantity(batch.getQuantity());
//	}


        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dtoPage));
    }

    @GetMapping(value = "/management/{productId}")
    public ResponseEntity<ApiResponse<ProductDetailManagementDTO>> getProductInDetailForManagement(@PathVariable Integer productId) {
        Product entity = productService.getProductById(productId);
        ProductDetailManagementDTO dto = modelMapper.map(entity, ProductDetailManagementDTO.class);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dto));
    }

    @GetMapping(value = "/public")
    public ResponseEntity<ApiResponse<Page<ProductSummaryManagementDTO>>> getAllProductForPublic(Pageable pageable,
                                                                                                 @RequestParam(name = "search", required = false) String search,
                                                                                                 ProductFilterFormRequest form) {
        form.setStatus(true);
        Page<Product> entities = productService.getAllProduct(pageable, search, form);
        List<ProductSummaryManagementDTO> dtos = modelMapper.map(entities.getContent(), new TypeToken<List<ProductSummaryManagementDTO>>() {}.getType());
        Page<ProductSummaryManagementDTO> dtoPage = new PageImpl<>(dtos, pageable, entities.getTotalElements());
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dtoPage));
    }

    @GetMapping(value = "/public/{productId}")
    public ResponseEntity<ApiResponse<ProductDetailManagementDTO>> getProductInDetailForPublic(@PathVariable Integer productId) {
        Product entity = productService.getProductById(productId);
        ProductDetailManagementDTO dto = modelMapper.map(entity, ProductDetailManagementDTO.class);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Success", dto));
    }

//    @PostMapping()
//    public ResponseEntity<ApiResponse<ProductSummaryManagementDTO>> createProduct(@ModelAttribute @Valid ProductFilterFormRequest form) throws IOException {
//        Product entity = productService.createProduct(form);
//        ProductSummaryManagementDTO dto = modelMapper.map(entity, ProductSummaryManagementDTO.class);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(HttpStatus.CREATED.value(), "Product created successfully", dto));
//    }
//
//    @PatchMapping()
//    public ResponseEntity<ApiResponse<ProductSummaryManagementDTO>> updateProduct(@ModelAttribute @Valid ProductFilterFormRequest form) {
//        Product entity = productService.updateProduct(form);
//        ProductSummaryManagementDTO dto = modelMapper.map(entity, ProductSummaryManagementDTO.class);
//        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), "Product updated successfully", dto));
//    }
}


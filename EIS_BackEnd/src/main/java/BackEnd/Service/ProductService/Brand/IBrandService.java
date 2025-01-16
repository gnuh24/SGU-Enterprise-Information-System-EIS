package BackEnd.Service.ProductService.Brand;

import BackEnd.Entity.ProductEntity.Brand;
import BackEnd.Form.ProductForm.BrandForm.BrandCreateForm;
import BackEnd.Form.ProductForm.BrandForm.BrandUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface IBrandService {

    List<Brand> getAllBrandNoPaging();

    Page<Brand> getAllBrand(Pageable pageable, String search);

    Brand getBrandById(Integer id);

    Brand createBrand(BrandCreateForm form) throws IOException, Exception;

    Brand updateBrand(BrandUpdateForm form) throws IOException, Exception;

    void deleteBrand(Integer brandId);

}

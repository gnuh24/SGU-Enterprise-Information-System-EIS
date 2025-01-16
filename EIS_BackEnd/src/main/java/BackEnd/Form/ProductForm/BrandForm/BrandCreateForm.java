package BackEnd.Form.ProductForm.BrandForm;


import BackEnd.Validation.FileContentType;
import BackEnd.Validation.FileSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BrandCreateForm {

    @NotBlank(message = "Bạn không được để trống tên thương hiệu !!")
    @Size(min = 3, max = 100, message = "Tên thương hiệu phải từ 3 đến 100 ký tự !!")
    private String brandName;

}

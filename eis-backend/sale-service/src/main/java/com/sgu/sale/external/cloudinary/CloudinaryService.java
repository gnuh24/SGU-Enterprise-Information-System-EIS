package com.sgu.sale.external.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Value("${cloudinary.folder}")
    private String folder;

    /**
     * Upload ảnh lên Cloudinary
     *
     * @param file    Ảnh cần upload (MultipartFile)
     * @return publicId của ảnh trên Cloudinary
     * @throws IOException nếu có lỗi xảy ra khi upload
     */
    public String uploadImage(MultipartFile file) throws IOException {
	Map uploadResult = cloudinary.uploader().upload(file.getBytes(),
		ObjectUtils.asMap("folder", folder));
	return (String) uploadResult.get("public_id");
    }

    /**
     * Xóa ảnh khỏi Cloudinary
     *
     * @param publicId ID ảnh trên Cloudinary (VD: "WineGallery/image_4_zv9on9")
     * @return true nếu xóa thành công, false nếu thất bại
     * @throws IOException nếu có lỗi xảy ra khi xóa
     */
    public boolean deleteImage(String publicId) throws IOException {
	Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
	return "ok".equals(deleteResult.get("result"));
    }
}

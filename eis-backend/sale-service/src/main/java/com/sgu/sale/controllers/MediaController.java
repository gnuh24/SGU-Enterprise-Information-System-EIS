package com.sgu.sale.controllers;

import com.sgu.sale.apiresponse.ApiResponse;
import com.sgu.sale.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/media")

public class MediaController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("/{mediaId}")
    public ResponseEntity<Resource> getMedia(@PathVariable String mediaId) throws MalformedURLException {
        // Logic for fetching media
        Resource resource = mediaService.getResouceByMediaId(mediaId);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadMedia(@ModelAttribute MultipartFile file) throws IOException {
        String path = mediaService.saveImage(file);
        // Logic for uploading media
        return ResponseEntity.ok(new ApiResponse<>(200, "Media uploaded successfully", path));
    }


}

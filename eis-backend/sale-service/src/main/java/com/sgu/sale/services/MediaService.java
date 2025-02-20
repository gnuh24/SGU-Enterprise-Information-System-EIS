package com.sgu.sale.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface MediaService {
    Resource getResouceByMediaId(String mediaId) throws MalformedURLException;
    String saveImage(MultipartFile image) throws IOException;
}

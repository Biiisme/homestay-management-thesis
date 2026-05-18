package com.example.homestaymanager.service;

import com.example.homestaymanager.dto.response.UploadImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    UploadImageResponse uploadImage(MultipartFile file, String folder);

    void deleteImage(String publicId);
}

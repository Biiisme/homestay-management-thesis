package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.dto.response.UploadImageResponse;
import com.example.homestaymanager.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final CloudinaryService cloudinaryService;

    @PostMapping("/uploads/image")
    public ApiResponse<UploadImageResponse> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder
    ) {
        UploadImageResponse response = cloudinaryService.uploadImage(file, folder);
        return ApiResponse.of(ApiStatus.OK, "Upload image successfully", response);
    }

    @DeleteMapping("/uploads/image")
    public ApiResponse<?> deleteImage(@RequestParam("publicId") String publicId) {
        cloudinaryService.deleteImage(publicId);
        return ApiResponse.of(ApiStatus.OK, "Delete image successfully", null);
    }
}

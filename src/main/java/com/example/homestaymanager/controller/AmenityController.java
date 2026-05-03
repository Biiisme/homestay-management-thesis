package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.model.Amenity;
import com.example.homestaymanager.model.Employee;
import com.example.homestaymanager.service.AmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AmenityController {

    private final AmenityService amenityService;

    @PostMapping("/amenities")
    public ApiResponse<Integer> createAmenity(@RequestBody Amenity amenity) {
        int i= amenityService.createAmenity(amenity);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED, amenity.getId());
    }

    @GetMapping("/amenities/{amenityId}")
    public ApiResponse<Amenity> getAmenityById(@PathVariable int amenityId) {
        Amenity amenity = amenityService.getAmenityByID(amenityId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS,amenity);
    }


    @DeleteMapping("/amenities/{amenityId}")
    public ApiResponse<?> deleteAmenityById(@PathVariable int amenityId) {
        amenityService.deleteAmenityById(amenityId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED,null);
    }
}
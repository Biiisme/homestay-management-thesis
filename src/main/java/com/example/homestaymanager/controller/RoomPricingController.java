package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.model.RoomPricing;
import com.example.homestaymanager.service.RoomPricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomPricingController {

    private final RoomPricingService roomPricingService;

    @PostMapping("/roomPricings")
    public ApiResponse<Integer> createRoomPricing(@RequestBody RoomPricing roomPricing) {
        int i= roomPricingService.createRoomPricing(roomPricing);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED,roomPricing.getId());
    }

    @GetMapping("/roomPricings/{roomPricingId}")
    public ApiResponse<RoomPricing> getRoomPricingById(@PathVariable int roomPricingId) {
        RoomPricing roomPricing= roomPricingService.getRoomPricingByID(roomPricingId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS,roomPricing);
    }

    @DeleteMapping("/roomPricings/{roomPricingId}")
    public ApiResponse<?> deleteRoomPricingById(@PathVariable int roomPricingId) {
        roomPricingService.deleteRoomPricingById(roomPricingId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED,null);
    }
}
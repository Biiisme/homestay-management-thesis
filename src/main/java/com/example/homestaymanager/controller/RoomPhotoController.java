package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.model.RoomPhoto;
import com.example.homestaymanager.service.RoomPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomPhotoController {

    private final RoomPhotoService roomPhotoService;

    @PostMapping("/roomPhotos")
    public ApiResponse<Integer> createRoomPhoto(@RequestBody RoomPhoto roomPhoto) {
        int i= roomPhotoService.createRoomPhoto(roomPhoto);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED,roomPhoto.getId());
    }

    @GetMapping("/roomPhotos/{roomPhotoId}")
    public ApiResponse<RoomPhoto> getRoomPhotoById(@PathVariable int roomPhotoId) {
        RoomPhoto roomPhoto = roomPhotoService.getRoomPhotoByID(roomPhotoId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS,roomPhoto);
    }

    @DeleteMapping("/roomPhotos/{roomPhotoId}")
    public ApiResponse<?> deleteRoomPhotoById(@PathVariable int roomPhotoId) {
        roomPhotoService.deleteRoomPhotoById(roomPhotoId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED,null);
    }
}
package com.example.homestaymanager.controller;
import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.model.Room;
import com.example.homestaymanager.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms")
    public ApiResponse<Integer> createRoom(@RequestBody Room room) {

        int i= roomService.createRoom(room);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED,room.getId());
    }

    @GetMapping("/rooms/{roomId}")
    public ApiResponse<Room> getRoomById(@PathVariable int roomId) {

        Room room= roomService.getRoomByID(roomId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS,room);
    }

    @DeleteMapping("/rooms/{roomId}")
    public ApiResponse<?> deleteRoomById(@PathVariable int roomId) {

        roomService.deleteRoomById(roomId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.DELETED,null);
    }
}
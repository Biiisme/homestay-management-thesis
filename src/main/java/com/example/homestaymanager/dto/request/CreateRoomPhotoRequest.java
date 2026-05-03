package com.example.homestaymanager.dto.request;
import lombok.Data;

@Data
public class CreateRoomPhotoRequest {
    private Integer roomId;
    private String photo;
}
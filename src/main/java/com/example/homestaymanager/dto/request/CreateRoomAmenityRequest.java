package com.example.homestaymanager.dto.request;
import lombok.Data;

@Data
public class CreateRoomAmenityRequest {
    private Integer roomId;
    private Integer amenityId;
    private int quantity;
}

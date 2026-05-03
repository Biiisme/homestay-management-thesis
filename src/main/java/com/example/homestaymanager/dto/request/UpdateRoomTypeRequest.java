package com.example.homestaymanager.dto.request;
import lombok.Data;

@Data
public class UpdateRoomTypeRequest {
    private String name;
    private String description;
    private Integer maxGuest;
    private String image;
}
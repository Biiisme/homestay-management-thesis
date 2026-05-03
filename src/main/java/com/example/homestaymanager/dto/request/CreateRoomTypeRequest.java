package com.example.homestaymanager.dto.request;
import lombok.Data;

@Data
public class CreateRoomTypeRequest {
    private String name;
    private String description;
    private int maxGuest;
    private String image;
}
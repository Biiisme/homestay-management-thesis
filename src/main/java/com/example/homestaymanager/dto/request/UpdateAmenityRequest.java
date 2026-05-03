package com.example.homestaymanager.dto.request;
import lombok.Data;

@Data
public class UpdateAmenityRequest {
    private Integer categoryId;
    private String name;
}
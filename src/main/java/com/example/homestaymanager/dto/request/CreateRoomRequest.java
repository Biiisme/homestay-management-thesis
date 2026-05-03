package com.example.homestaymanager.dto.request;


import lombok.Data;


@Data
public class CreateRoomRequest {
    private Integer branchId;
    private Integer roomTypeId;
    private int number;
    private float area;
    private String thumbnail;
}

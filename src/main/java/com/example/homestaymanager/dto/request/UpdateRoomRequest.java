package com.example.homestaymanager.dto.request;
import lombok.Data;

@Data
public class UpdateRoomRequest {

    private Integer branchId;
    private Integer roomTypeId;
    private Integer number;       // dùng Integer để cho phép null
    private Float area;
    private String thumbnail;
}
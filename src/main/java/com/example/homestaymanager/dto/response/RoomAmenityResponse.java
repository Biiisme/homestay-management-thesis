package com.example.homestaymanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomAmenityResponse {
    private int id;
    private int amenityId;
    private String amenityName;
    private String amenityDescription;
    private int quantity;
}

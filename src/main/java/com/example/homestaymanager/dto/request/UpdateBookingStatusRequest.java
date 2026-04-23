package com.example.homestaymanager.dto.request;

import com.example.homestaymanager.enums.BookingStatus;
import lombok.Data;

@Data
public class UpdateBookingStatusRequest {
    private BookingStatus status;
}

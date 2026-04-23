package com.example.homestaymanager.service;

import com.example.homestaymanager.dto.response.BookingResponse;
import com.example.homestaymanager.dto.request.CreateBookingRequest;
import com.example.homestaymanager.enums.BookingStatus;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);

    BookingResponse getBookingById(int id);

    BookingResponse updateStatus(int bookingId, BookingStatus newStatus);

    BookingResponse cancelBooking(int id);
}

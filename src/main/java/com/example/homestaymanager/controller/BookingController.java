package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;


import com.example.homestaymanager.dto.request.CreateBookingRequest;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.dto.response.BookingResponse;

import com.example.homestaymanager.dto.request.UpdateBookingStatusRequest;
import com.example.homestaymanager.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/bookings")
    public ApiResponse<BookingResponse> createBooking(@RequestBody CreateBookingRequest request) {
        BookingResponse data = bookingService.createBooking(request);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.CREATED, data);
    }

    @GetMapping("/bookings/{bookingId}")
    public ApiResponse<BookingResponse> getBookingById(@PathVariable int bookingId) {
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS, bookingService.getBookingById(bookingId));
    }

    @PatchMapping("/bookings/{bookingId}/status")
    public ApiResponse<BookingResponse> updateStatus(@PathVariable int bookingId, @RequestBody UpdateBookingStatusRequest body) {
        if (body == null) {
            return ApiResponse.of(ApiStatus.BAD_REQUEST, "Trạng thái truyền lên không được để trống", null);
        }
        BookingResponse data = bookingService.updateStatus(bookingId, body.getStatus());
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS, data);
    }

    @PostMapping("/bookings/{bookingId}/cancel")
    public ApiResponse<BookingResponse> cancelBooking(@PathVariable int bookingId) {
        BookingResponse data = bookingService.cancelBooking(bookingId);
        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS, data);
    }
}

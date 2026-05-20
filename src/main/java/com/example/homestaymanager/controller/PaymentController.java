package com.example.homestaymanager.controller;

import com.example.homestaymanager.constant.ApiMessage;
import com.example.homestaymanager.constant.ApiStatus;
import com.example.homestaymanager.dto.response.ApiResponse;
import com.example.homestaymanager.dto.response.BookingResponse;
import com.example.homestaymanager.dto.response.PaymentUrlResponse;
import com.example.homestaymanager.exception.UnauthorizedException;
import com.example.homestaymanager.security.SecurityUtil;
import com.example.homestaymanager.service.BookingService;
import com.example.homestaymanager.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final BookingService bookingService;
    private final VnPayService vnPayService;

    @PostMapping("/payments/vnpay/create/{bookingId}")
    public ApiResponse<PaymentUrlResponse> createVnPayUrl(@PathVariable int bookingId, HttpServletRequest request) {
        if (SecurityUtil.isCustomer()) {
            var booking = bookingService.getBookingById(bookingId);
            var current = SecurityUtil.getCurrentUser();
            if (current != null && booking.getCustomerId() != current.getId()) {
                throw new UnauthorizedException("Bạn chỉ có thể thanh toán booking của chính mình");
            }
        }

        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS, vnPayService.createPaymentUrl(bookingId, getClientIp(request)));
    }

    @PostMapping("/payments/demo-success/{bookingId}")
    public ApiResponse<BookingResponse> confirmDemoPayment(@PathVariable int bookingId) {
        if (SecurityUtil.isCustomer()) {
            var booking = bookingService.getBookingById(bookingId);
            var current = SecurityUtil.getCurrentUser();
            if (current != null && booking.getCustomerId() != current.getId()) {
                throw new UnauthorizedException("Bạn chỉ có thể thanh toán booking của chính mình");
            }
        }

        return ApiResponse.of(ApiStatus.OK, ApiMessage.SUCCESS, vnPayService.confirmDemoPayment(bookingId));
    }

    private static String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}

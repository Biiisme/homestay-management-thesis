package com.example.homestaymanager.service;

import com.example.homestaymanager.dto.response.BookingResponse;
import com.example.homestaymanager.dto.response.PaymentUrlResponse;
import com.example.homestaymanager.enums.BookingStatus;
import com.example.homestaymanager.repository.BookingRepository;
import com.example.homestaymanager.service.impl.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

@Service
@RequiredArgsConstructor
public class VnPayService {

    private static final DateTimeFormatter DEMO_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final BookingService bookingService;
    private final BookingRepository bookingRepository;

    @Value("${vnpay.pay-url:https://sandbox.vnpayment.vn/paymentv2/vpcpay.html}")
    private String payUrl;

    @Value("${vnpay.tmn-code:}")
    private String tmnCode;

    @Value("${vnpay.hash-secret:}")
    private String hashSecret;

    @Value("${vnpay.return-url:http://localhost:5173/home/payment-result}")
    private String returnUrl;

    public PaymentUrlResponse createPaymentUrl(int bookingId, String clientIp) {
        BookingResponse booking = bookingService.getBookingById(bookingId);
        if (booking.getCurrentStatus() != BookingStatus.PENDING && booking.getCurrentStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException("Booking không ở trạng thái có thể thanh toán");
        }
        if (booking.getTotalAmount() == null || booking.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Booking chưa có số tiền cần thanh toán");
        }

        if (isBlank(tmnCode) || isBlank(hashSecret)) {
            return PaymentUrlResponse.builder()
                    .demoMode(true)
                    .paymentUrl(returnUrl + "?vnp_ResponseCode=00&vnp_TxnRef=" + bookingId + "&demo=true")
                    .message("Chưa cấu hình VNPAY_TMN_CODE/VNPAY_HASH_SECRET, dùng thanh toán demo nội bộ.")
                    .build();
        }

        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", tmnCode);
        params.put("vnp_Amount", booking.getTotalAmount().multiply(BigDecimal.valueOf(100)).toBigInteger().toString());
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", String.valueOf(booking.getId()));
        params.put("vnp_OrderInfo", "Thanh toan booking #" + booking.getId());
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", returnUrl);
        params.put("vnp_IpAddr", isBlank(clientIp) ? "127.0.0.1" : clientIp);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
        params.put("vnp_CreateDate", formatter.format(new Date()));
        params.put("vnp_ExpireDate", LocalDateTime.now().plusMinutes(15).format(DEMO_TIME_FORMAT));

        String hashData = buildQuery(params, true);
        String secureHash = hmacSha512(hashSecret, hashData);
        String paymentUrl = payUrl + "?" + buildQuery(params, true) + "&vnp_SecureHash=" + secureHash;

        return PaymentUrlResponse.builder()
                .demoMode(false)
                .paymentUrl(paymentUrl)
                .message("Đã tạo URL thanh toán VNPay sandbox.")
                .build();
    }

    @Transactional
    public BookingResponse confirmDemoPayment(int bookingId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking không tồn tại"));
        booking.setPaidAmount(booking.getTotalAmount());
        return BookingServiceImpl.toResponse(booking);
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String buildQuery(Map<String, String> params, boolean encodeValue) {
        StringBuilder query = new StringBuilder();
        params.forEach((key, value) -> {
            if (query.length() > 0) {
                query.append('&');
            }
            query.append(urlEncode(key));
            query.append('=');
            query.append(encodeValue ? urlEncode(value) : value);
        });
        return query.toString();
    }

    private static String urlEncode(String value) {
        return URLEncoder.encode(value, StandardCharsets.US_ASCII);
    }

    private static String hmacSha512(String key, String data) {
        try {
            Mac hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            hmac.init(secretKey);
            byte[] bytes = hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder hash = new StringBuilder(bytes.length * 2);
            for (byte item : bytes) {
                hash.append(String.format("%02x", item));
            }
            return hash.toString();
        } catch (Exception exception) {
            throw new RuntimeException("Không thể tạo chữ ký VNPay", exception);
        }
    }
}

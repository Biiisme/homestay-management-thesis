package com.example.homestaymanager.dto.request;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateRoomPricingRequest {

    private String baseDuration;
    private BigDecimal basePrice;
    private BigDecimal weekendPrice;
    private BigDecimal holidayPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String policy;
    private Boolean status;
}
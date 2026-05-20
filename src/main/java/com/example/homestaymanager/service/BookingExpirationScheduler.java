package com.example.homestaymanager.service;

import com.example.homestaymanager.enums.BookingStatus;
import com.example.homestaymanager.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BookingExpirationScheduler {

    private final BookingRepository bookingRepository;

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void cancelExpiredPendingBookings() {
        bookingRepository.findByCurrentStatusAndPendingExpiresAtBefore(BookingStatus.PENDING, LocalDateTime.now())
                .forEach((booking) -> booking.setCurrentStatus(BookingStatus.CANCELLED));
    }
}

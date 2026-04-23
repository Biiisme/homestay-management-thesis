package com.example.homestaymanager.repository;

import com.example.homestaymanager.enums.BookingStatus;
import com.example.homestaymanager.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query("""
            select count(b) from Booking b
            where b.room.id = :roomId
            and b.currentStatus in :statuses
            and b.checkIn < :checkOut
            and b.checkOut > :checkIn
            """)
    long countOverlapping(
            @Param("roomId") int roomId,
            @Param("checkIn") LocalDateTime checkIn,
            @Param("checkOut") LocalDateTime checkOut,
            @Param("statuses") Collection<BookingStatus> statuses);
}

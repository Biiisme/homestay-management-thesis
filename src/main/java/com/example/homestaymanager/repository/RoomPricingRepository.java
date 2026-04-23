package com.example.homestaymanager.repository;

import com.example.homestaymanager.model.RoomPricing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomPricingRepository extends JpaRepository<RoomPricing, Integer> {

    @Query("""
            select rp from RoomPricing rp
            where rp.roomType.id = :roomTypeId
            and (rp.status = true or rp.status is null)
            and rp.startDate <= :stayEnd
            and (rp.endDate is null or rp.endDate >= :stayStart)
            """)
    List<RoomPricing> findApplicableForStay(
            @Param("roomTypeId") int roomTypeId,
            @Param("stayStart") LocalDateTime stayStart,
            @Param("stayEnd") LocalDateTime stayEnd);
}
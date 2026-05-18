package com.example.homestaymanager.repository;

import com.example.homestaymanager.model.RoomAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomAmenityRepository extends JpaRepository<RoomAmenity, Integer> {
    List<RoomAmenity> findByRoomId(int roomId);

    void deleteByRoomId(int roomId);
}

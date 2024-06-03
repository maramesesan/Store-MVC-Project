package org.example.spring1.room;

import org.example.spring1.room.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {


    List<Room> findRoomsByAccommodationId (Long accommodationId);
    Room findRoomsById(Long id);
}

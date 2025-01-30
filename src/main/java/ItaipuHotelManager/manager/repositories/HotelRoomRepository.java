package ItaipuHotelManager.manager.repositories;

import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
    HotelRoom findByRoomNumber(String room);
    List<HotelRoom> findByStatus(RoomStatus status);
}

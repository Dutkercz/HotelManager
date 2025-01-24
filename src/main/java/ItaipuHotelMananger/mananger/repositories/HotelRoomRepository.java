package ItaipuHotelMananger.mananger.repositories;

import ItaipuHotelMananger.mananger.entities.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Long> {
}

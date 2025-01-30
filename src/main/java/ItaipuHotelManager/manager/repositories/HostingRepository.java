package ItaipuHotelManager.manager.repositories;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostingRepository extends JpaRepository<Hosting, Long> {

    Double basePrice (double price);

    Hosting findByRoomRoomNumber(String room);
}

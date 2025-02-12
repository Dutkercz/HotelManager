package ItaipuHotelManager.manager.repositories;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostingRepository extends JpaRepository<Hosting, Long> {

    List<Hosting> findByClient(HotelClient client);

    List<Hosting> findByStatus(RoomStatus roomStatus);

    Hosting findByStatusAndRoomRoomNumber(RoomStatus roomStatus, String roomNumber);
}

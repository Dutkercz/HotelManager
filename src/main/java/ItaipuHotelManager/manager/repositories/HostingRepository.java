package ItaipuHotelManager.manager.repositories;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostingRepository extends JpaRepository<Hosting, Long> {

    Hosting findByRoomRoomNumber(String roomNumber);

    List<Hosting> findByClientAndRoomStatus(HotelClient client, RoomStatus status);

    List<Hosting> findByClient (HotelClient client);

    List<Hosting> findByRoomStatus(RoomStatus roomStatus);
}

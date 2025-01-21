package ItaipuHotelMananger.mananger.repositories;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelClientRepository extends JpaRepository<HotelClient, Long> {
}

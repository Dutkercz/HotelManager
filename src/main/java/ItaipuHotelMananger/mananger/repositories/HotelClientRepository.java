package ItaipuHotelMananger.mananger.repositories;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelClientRepository extends JpaRepository<HotelClient, Long> {
}

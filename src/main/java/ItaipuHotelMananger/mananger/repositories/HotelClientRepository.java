package ItaipuHotelMananger.mananger.repositories;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelClientRepository extends JpaRepository<HotelClient, Long> {
    Optional<HotelClient> findByCpf(String cpf);

}

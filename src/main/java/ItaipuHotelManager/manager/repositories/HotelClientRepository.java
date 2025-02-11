package ItaipuHotelManager.manager.repositories;

import ItaipuHotelManager.manager.entities.HotelClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelClientRepository extends JpaRepository<HotelClient, Long> {
    HotelClient findByCpf(String cpf);

    void deleteByCpf(String cpf);
}

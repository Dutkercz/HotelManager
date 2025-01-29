package ItaipuHotelManager.manager.repositories;

import ItaipuHotelManager.manager.entities.HotelPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelPersonRepository extends JpaRepository<HotelPerson, Long> {
}

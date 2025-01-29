package ItaipuHotelMananger.mananger.repositories;

import ItaipuHotelMananger.mananger.entities.HotelPerson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelPersonRepository extends JpaRepository<HotelPerson, Long> {
}

package ItaipuHotelMananger.mananger.repositories;

import ItaipuHotelMananger.mananger.entities.Hosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostingRepository extends JpaRepository<Hosting, Long> {

    public Double basePrice (double price);
}

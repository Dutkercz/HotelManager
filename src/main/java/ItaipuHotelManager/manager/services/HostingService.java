package ItaipuHotelManager.manager.services;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.repositories.HostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostingService {
    @Autowired
    HostingRepository hostingRepository;

    public Hosting findByRoomNumber (String room){
        Hosting hosting = new Hosting();
        return hostingRepository.findByRoomRoomNumber(room);
    }

    public Double hostingTotalPriceDebit(Double valor){
        return hostingRepository.basePrice(valor*1.03);
    }

    public Double hostingTotalPriceCredit(Double valor){
        return hostingRepository.basePrice(valor*1.05);
    }

    public void hostingTotalPrice(Double valor){
        hostingRepository.basePrice(valor);
    }

    public void saveHosting(Hosting hosting) {
        hostingRepository.save(hosting);
    }
}

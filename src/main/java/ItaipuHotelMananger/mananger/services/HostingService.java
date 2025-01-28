package ItaipuHotelMananger.mananger.services;

import ItaipuHotelMananger.mananger.entities.Hosting;
import ItaipuHotelMananger.mananger.repositories.HostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostingService {
    @Autowired
    HostingRepository hostingRepository;

    public Double hostingTotalPriceDebit(Hosting hosting){
        return hostingRepository.basePrice(hosting.getBasePrice()*1.03);
    }
    public Double hostingTotalPriceCredit(Hosting hosting){
        return hostingRepository.basePrice(hosting.getBasePrice()*1.05);
    }
    public Double hostingTotalPrice(Hosting hosting){
        return hostingRepository.basePrice(hosting.getBasePrice());
    }

    public void saveHosting(Hosting hosting) {
        hostingRepository.save(hosting);
    }
}

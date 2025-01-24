package ItaipuHotelMananger.mananger.services;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.repositories.HotelClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelClientService {
    @Autowired
    HotelClientRepository hotelClientRepository;

    public List<HotelClient> findAll(){
        List<HotelClient> list = hotelClientRepository.findAll();
        return list;
    }
    public HotelClient insert(HotelClient o){
        return hotelClientRepository.save(o);
    }
}



package ItaipuHotelMananger.mananger.services;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.repositories.HotelClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelClientService {
    @Autowired
    HotelClientRepository hotelClientRepository;

    public List<HotelClient> findAll(){
        return hotelClientRepository.findAll();
    }

    public HotelClient findByCpf(String cpf){
        return hotelClientRepository.findByCpf(cpf);
    }

    public HotelClient insert(HotelClient o){
        return hotelClientRepository.save(o);
    }
}



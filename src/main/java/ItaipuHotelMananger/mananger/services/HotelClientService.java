package ItaipuHotelMananger.mananger.services;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.repositories.HotelClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelClientService {
    @Autowired
    HotelClientRepository hotelClientRepository;

    public List<HotelClient> findAll(){
        List<HotelClient> list = hotelClientRepository.findAll();
        return list;
    }

    public HotelClient findByCpf(String cpf){
        Optional<HotelClient> client = hotelClientRepository.findByCpf(cpf);
        return client.orElseThrow(() -> new RuntimeException("Cliente com cpf " + cpf + " não encontrado."));
    }



    public HotelClient insert(HotelClient o){
        return hotelClientRepository.save(o);
    }
}



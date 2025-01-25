package ItaipuHotelMananger.mananger.services;

import ItaipuHotelMananger.mananger.entities.HotelRoom;
import ItaipuHotelMananger.mananger.repositories.HotelRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelRoomService {

    @Autowired
    private HotelRoomRepository repository;

    public List<HotelRoom> findAll(){
        return repository.findAll();
    }
    public HotelRoom findRoom(Long id){
        return repository.findById(id).orElseThrow();
    }

    public HotelRoom saveRoom(HotelRoom o){
        return repository.save(o);
    }

    public List<HotelRoom> listOfRoom(){
        return repository.findAll();
    }
}

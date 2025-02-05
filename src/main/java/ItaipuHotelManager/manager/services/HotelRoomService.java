package ItaipuHotelManager.manager.services;

import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import ItaipuHotelManager.manager.repositories.HotelRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.PanelUI;
import java.util.List;

@Service
public class HotelRoomService {

    @Autowired
    private HotelRoomRepository repository;

    @Transactional
    public List<HotelRoom> findAll(){
        return repository.findAll();
    }

    public HotelRoom findRoom(Long id){
        return repository.findById(id).orElseThrow();
    }

    public HotelRoom saveRoom(HotelRoom o){
        return repository.save(o);
    }

    public HotelRoom findByRoomNumber (String room){
        return repository.findByRoomNumber(room);
    }

    @Transactional
    public void updateRoomStatus(String aptoNumber, HotelClient client){
        HotelRoom room = repository.findByRoomNumber(aptoNumber);
        room.setClient(client);
        room.setStatus(RoomStatus.OCUPADO);
        repository.save(room);
    }

    public List<HotelRoom> getAvailableRooms() {
        return repository.findByStatus(RoomStatus.DISPONIVEL);
    }


    public List<HotelRoom> getOccupiedRooms() {
        return repository.findByStatus(RoomStatus.OCUPADO);
    }

    public List<HotelRoom> findByStatus(RoomStatus status){
        return repository.findByStatus(status);
    }
}

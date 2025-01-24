package ItaipuHotelMananger.mananger.Config;

import ItaipuHotelMananger.mananger.entities.HotelRoom;
import ItaipuHotelMananger.mananger.repositories.HotelRoomRepository;
import ItaipuHotelMananger.mananger.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;


public class ConfigRooms implements CommandLineRunner {

    @Autowired
    private HotelRoomService service;
    @Autowired
    private HotelRoomRepository repository;


    @Override
    public void run(String... args) throws Exception {
        service.saveRoom(repository.save(new HotelRoom(null, "01", 1, 1)));
        service.saveRoom(repository.save(new HotelRoom(null, "02", 1, 0)));
        service.saveRoom(repository.save(new HotelRoom(null, "03", 1, 1)));
        service.saveRoom(repository.save(new HotelRoom(null, "04", 1, 2)));
        service.saveRoom(repository.save(new HotelRoom(null, "05", 0, 2)));
        service.saveRoom(repository.save(new HotelRoom(null, "06", 0, 2)));
        service.saveRoom(repository.save(new HotelRoom(null, "07", 1, 2)));
        service.saveRoom(repository.save(new HotelRoom(null, "08", 1, 1)));
        service.saveRoom(repository.save(new HotelRoom(null, "09", 0, 2)));
        service.saveRoom(repository.save(new HotelRoom(null, "10", 0, 2)));
        service.saveRoom(repository.save(new HotelRoom(null, "11", 0, 2)));
        service.saveRoom(repository.save(new HotelRoom(null, "12", 0, 2)));
        service.saveRoom(repository.save(new HotelRoom(null, "13", 0, 3)));
        service.saveRoom(repository.save(new HotelRoom(null, "13", 0, 3)));
    }
}

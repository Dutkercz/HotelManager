package ItaipuHotelManager.manager.controllers;

import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rooms")
public class HotelRoomController {

    @Autowired
    private HotelRoomService roomService;

    @GetMapping
    public ResponseEntity<Map<String, List<HotelRoom>>> getRoomByStatus() {
        List<HotelRoom> availableRooms = roomService.getAvailableRooms();
        List<HotelRoom> occupiedRooms = roomService.getOccupiedRooms();

        Map<String, List<HotelRoom>> response = new HashMap<>();
        response.put("Livres", availableRooms);
        response.put("Ocupados", occupiedRooms);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<HotelRoom>> findAll() {
        List<HotelRoom> hotelRooms = roomService.findAll();
        return ResponseEntity.ok().body(hotelRooms);
    }

    @GetMapping(value = "/available")
    public ResponseEntity<List<HotelRoom>> getAvailableRooms() {
        return ResponseEntity.ok().body(roomService.getAvailableRooms());
    }

    @GetMapping(value = "/occupied")
    ResponseEntity<List<HotelRoom>> getOccupiedRooms() {
        return ResponseEntity.ok().body(roomService.getOccupiedRooms());
    }

    @GetMapping(value = "/{roomNumber}")
    public ResponseEntity<HotelRoom> findByRoomNumber(@PathVariable String roomNumber) {
        return ResponseEntity.ok().body(roomService.findByRoomNumber(roomNumber));
    }
}

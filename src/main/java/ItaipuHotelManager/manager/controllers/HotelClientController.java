package ItaipuHotelManager.manager.controllers;

import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.services.HotelClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class HotelClientController {

    @Autowired
    HotelClientService hotelClientService;

    @GetMapping
    public ResponseEntity<List<HotelClient>> findAll(){
        List<HotelClient> list = hotelClientService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<HotelClient> postClient(@RequestBody HotelClient client){
        HotelClient obj = hotelClientService.insert(client);
        return ResponseEntity.ok().body(obj);
    }

}

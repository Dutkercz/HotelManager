package ItaipuHotelMananger.mananger.controllers;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class HotelClientController {

    @Autowired
    HotelClientService hotelClientService;

    @GetMapping
    public List<HotelClient> findAll(){
        List<HotelClient> list = hotelClientService.findAll();
        return list;
    }

    @PostMapping
    public ResponseEntity<HotelClient> postClient(@RequestBody HotelClient client){
        HotelClient obj = hotelClientService.insert(client);
        return ResponseEntity.ok().body(obj);
    }

}

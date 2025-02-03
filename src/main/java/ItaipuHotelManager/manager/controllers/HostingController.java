package ItaipuHotelManager.manager.controllers;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.services.HostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "hosting")
public class HostingController {

    @Autowired
    HostingService hostingService;

    @GetMapping
    public ResponseEntity<List<Hosting>> findAllHosting (){
        List<Hosting> hostingList = hostingService.findAll();
        return ResponseEntity.ok().body(hostingList);
    }
}

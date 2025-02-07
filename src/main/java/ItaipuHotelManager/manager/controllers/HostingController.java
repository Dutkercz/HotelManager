package ItaipuHotelManager.manager.controllers;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.services.HostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/hosting")
public class HostingController {

    @Autowired
    HostingService hostingService;

    @GetMapping
    public ResponseEntity<List<Hosting>> findAllHosting (){
        List<Hosting> hostingList = hostingService.findAll();
        return ResponseEntity.ok().body(hostingList);
    }

    @GetMapping(value = "/{cpf}/all")
    public ResponseEntity<List<Hosting>> findHostingByClient(@PathVariable String cpf){
        List<Hosting> hostingList = hostingService.findAllHostings(cpf);
        return ResponseEntity.ok().body(hostingList);
    }

    @PostMapping(value = "/checkin")
    public ResponseEntity<Hosting> checkIn(@RequestBody Hosting hosting){
        hostingService.checkIn(hosting, hosting.getClient(), hosting.getRoom());
        return ResponseEntity.ok().body(hosting);
    }

}

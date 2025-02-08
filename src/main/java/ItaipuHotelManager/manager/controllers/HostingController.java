package ItaipuHotelManager.manager.controllers;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.services.HostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/allhosting")
public class HostingController {

    @Autowired
    HostingService hostingService;

    @PostMapping(value = "/checkin")
    public ResponseEntity<Hosting> checkIn(@RequestBody Hosting hosting){
        hostingService.checkIn(hosting, hosting.getClient(), hosting.getRoom());
        return ResponseEntity.ok().body(hosting);
    }

    @PostMapping(value = "/checkout")
    public ResponseEntity<Hosting> checkOut(@RequestBody Hosting hosting){
        hostingService.checkOut(hosting);
        return ResponseEntity.ok().body(hosting);
    }

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

    @GetMapping(value = "/active")
    public ResponseEntity<List<Hosting>> getActiveHostig(){
        List<Hosting> hostingList = hostingService.findActiveHosting();
        return ResponseEntity.ok().body(hostingList);
    }

    @GetMapping(value = "/room/{roomNumber}")
    public ResponseEntity<Hosting> getByRoomNumber(@PathVariable String roomNumber){
        return ResponseEntity.ok().body(hostingService.findByRoomNumber(roomNumber));
    }

}

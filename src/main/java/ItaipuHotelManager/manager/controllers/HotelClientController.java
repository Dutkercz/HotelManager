package ItaipuHotelManager.manager.controllers;

import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.services.HotelClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clients")
public class HotelClientController {

    @Autowired
    HotelClientService clientService;

    @GetMapping
    public ResponseEntity<List<HotelClient>> findAll(){
        List<HotelClient> list = clientService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping
    public ResponseEntity<HotelClient> postClient(@RequestBody HotelClient client){
        if (clientService.findByCpf(client.getCpf()) != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        HotelClient obj = clientService.insert(client);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{cpf}")
    public ResponseEntity<HotelClient> findByCpf(@PathVariable String cpf){
        HotelClient client = clientService.findByCpf(cpf);
        return ResponseEntity.ok(client);
    }
    @DeleteMapping(value = "/delete/{cpf}")
    public ResponseEntity<Void> deleteByCpf(@PathVariable String cpf){
        clientService.deleteByCpf(cpf);
        return ResponseEntity.noContent().build();
    }
    @PutMapping(value = "/update/{cpf}")
    public ResponseEntity<HotelClient> updateClient (@PathVariable String cpf, @RequestBody HotelClient clientToUpdate){
        HotelClient client = clientService.updateClient(cpf, clientToUpdate);
        return ResponseEntity.ok().body(client);
    }

}

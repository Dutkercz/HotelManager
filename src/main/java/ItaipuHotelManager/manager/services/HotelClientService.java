package ItaipuHotelManager.manager.services;

import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.repositories.HotelClientRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelClientService {
    @Autowired
    HotelClientRepository hotelClientRepository;

    @Transactional
    public List<HotelClient> findAll() {
        return hotelClientRepository.findAll();
    }

    @Transactional
    public HotelClient findByCpf(String cpf) {
        HotelClient client = hotelClientRepository.findByCpf(cpf);
        if (client != null) {
            Hibernate.initialize(client);
        }
        return client;
    }

    @Transactional
    public HotelClient insert(HotelClient o) {
        return hotelClientRepository.save(o);
    }

    @Transactional
    public void deleteByCpf(String cpf) {
        hotelClientRepository.deleteByCpf(cpf);
    }

    @Transactional
    public HotelClient updateClient(String cpf, HotelClient clientToUpdate) {
        try {
            HotelClient client = findByCpf(cpf);
            client.setFullName(clientToUpdate.getFullName());
            client.setCity(clientToUpdate.getCity());
            client.setAddress(clientToUpdate.getAddress());
            client.setEmail(clientToUpdate.getEmail());
            client.setPhone(clientToUpdate.getPhone());
            client.setCnpj(clientToUpdate.getCnpj());
            return hotelClientRepository.save(client);
        } catch (NullPointerException e) {
            throw new RuntimeException("Cliente não encontrado");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}

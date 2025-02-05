package ItaipuHotelManager.manager.services;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import ItaipuHotelManager.manager.repositories.HostingRepository;
import ItaipuHotelManager.manager.repositories.HotelRoomRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class HostingService {
    @Autowired
    private HostingRepository hostingRepository;
    @Autowired
    private HotelRoomRepository roomRepository;
    @Autowired
    private HotelClientService clientService;

    public Double hostingTotalPriceDebit(Hosting hosting){
        return hosting.getBasePrice()*1.03;
    }

    public Double hostingTotalPriceCredit(Hosting hosting){
        return hosting.getBasePrice()*1.05;
    }

    public Double hostingTotalPrice(Hosting hosting){
        return hosting.getBasePrice();
    }

    public void saveHosting(Hosting hosting) {
        hostingRepository.save(hosting);
    }

    @Transactional
    public String checkOut(String cpfCheckOut, String paymentMethod) {

        HotelClient client = clientService.findByCpf(cpfCheckOut);
        if (client == null){
            return "**** Cliente não encontrado. ****\n" +
                    "===================================";
        }

        try{
            List<Hosting> hosting = hostingRepository.findByClientAndRoomStatus(client, RoomStatus.OCUPADO);
            Hosting hostingToCheckOut = hosting.stream()
                    .filter(x -> x.getStatus() == RoomStatus.OCUPADO)
                    .findFirst().orElseThrow();

            Hibernate.initialize(hostingToCheckOut);
            Hibernate.initialize(hostingToCheckOut.getPersons());

            System.out.println("=======================");
            System.out.println("Hospedagem encontrada ");
            System.out.println("=======================");

            double totalPrice = switch (paymentMethod) {
                case "1" -> hostingTotalPrice(hostingToCheckOut);
                case "2" -> hostingTotalPriceCredit(hostingToCheckOut);
                case "3" -> hostingTotalPriceDebit(hostingToCheckOut);
                default -> 0.0;
            };
            hostingToCheckOut.setBasePrice(totalPrice);

            HotelRoom room = hostingToCheckOut.getRoom();
            room.setStatus(RoomStatus.DISPONIVEL);
            room.setClient(null);
            hostingToCheckOut.setStatus(RoomStatus.FINALIZADO);
            hostingToCheckOut.setCheckOut(LocalDateTime.now());
            roomRepository.save(room);
            hostingRepository.save(hostingToCheckOut);
            System.out.println(hostingToCheckOut);
            return "**** CheckOut realizado com sucesso! Total R$ "
                    + hostingToCheckOut.getBasePrice()+" ****\n";

        } catch (HibernateException | NoSuchElementException e) {
            return "==================================================" +
                    "\n**** Nenhuma hospedagem ativa para este CPF. ****" +
                    "\n==================================================\n";
        }
    }

    @Transactional
    public List<Hosting> findAllHostings (String cpf){
        HotelClient client = clientService.findByCpf(cpf);
        if (client == null){
            System.out.println("Cliente não encontrado.");
        }
        return hostingRepository.findByClient(client);
    }

    public List<Hosting> findAll() {
        return hostingRepository.findAll();
    }
}

package ItaipuHotelManager.manager.services;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelPerson;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import ItaipuHotelManager.manager.repositories.HostingRepository;
import ItaipuHotelManager.manager.repositories.HotelRoomRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HostingService {
    @Autowired
    private HostingRepository hostingRepository;

    @Autowired
    private HotelRoomRepository roomRepository;

    @Autowired
    private HotelClientService clientService;

//    public Hosting findByRoomNumber (String room){
//        Hosting hosting = new Hosting();
//        return hostingRepository.findByRoomRoomNumber(room);
//    }

//    public Double findBasePrice (Hosting hosting){
//        hostingRepository.findBasePrice(hosting);
//        return hosting.getBasePrice();
//    }

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
            return "Cliente não encontrado.";
        }

        Hosting hosting = hostingRepository.findByClientAndRoomStatus(client, RoomStatus.OCUPADO);
        Hibernate.initialize(hosting);


        System.out.println(hosting);
        if (hosting == null){
            return "Nenhuma hospedagem ativa para este CPF.";
        }
        System.out.println("=======================");
        System.out.println("Hospedagem encontrada ");
        System.out.println("=======================");

        double totalPrice = 0.0;
        if (paymentMethod.equals("1")){
            totalPrice = hostingTotalPrice(hosting);
        } else if (paymentMethod.equals("2")) {
           totalPrice = hostingTotalPriceCredit(hosting);
        } else if (paymentMethod.equals("3")) {
            totalPrice = hostingTotalPriceDebit(hosting);
        }
        hosting.setBasePrice(totalPrice);
        HotelRoom room = hosting.getRoom();
        room.setStatus(RoomStatus.DISPONIVEL);
        roomRepository.save(room);

        hosting.setCheckOut(LocalDateTime.now());
        hostingRepository.save(hosting);

        return "CheckOut realizado com sucesso! Total R$ ";

    }
}

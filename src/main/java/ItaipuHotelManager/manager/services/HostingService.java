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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

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
            return "Cliente não encontrado.";
        }

        try{
            List<Hosting> hosting = hostingRepository.findByClientAndRoomStatus(client, RoomStatus.OCUPADO);
            Hosting hostingToCheckOut = hosting.stream()
                    .filter(x -> x.getStatus() == RoomStatus.OCUPADO)
                    .findFirst().orElseThrow(() -> new RuntimeException("Nenhuma "));

            Hibernate.initialize(hostingToCheckOut);

            if (hostingToCheckOut == null){
                return "Nenhuma hospedagem ativa para este CPF.";
            }
            System.out.println("=======================");
            System.out.println("Hospedagem encontrada ");
            System.out.println("=======================");



            double totalPrice = 0.0;
            if (paymentMethod.equals("1")){
                totalPrice = hostingTotalPrice(hostingToCheckOut);
            } else if (paymentMethod.equals("2")) {
                totalPrice = hostingTotalPriceCredit(hostingToCheckOut);
            } else if (paymentMethod.equals("3")) {
                totalPrice = hostingTotalPriceDebit(hostingToCheckOut);
            }
            hostingToCheckOut.setBasePrice(totalPrice);

            HotelRoom room = hostingToCheckOut.getRoom();
            room.setStatus(RoomStatus.DISPONIVEL);
            hostingToCheckOut.setStatus(RoomStatus.FINALIZADO);
            hostingToCheckOut.setCheckOut(LocalDateTime.now());
            roomRepository.save(room);
            hostingRepository.save(hostingToCheckOut);

            return "CheckOut realizado com sucesso! Total R$ " + hostingToCheckOut.getBasePrice();
        } catch (HibernateException | NoSuchElementException e) {
            throw new RuntimeException(e);
        }

    }
}

package ItaipuHotelManager.manager.ui.utils;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelPerson;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import ItaipuHotelManager.manager.repositories.HotelPersonRepository;
import ItaipuHotelManager.manager.services.HostingService;
import ItaipuHotelManager.manager.services.HotelClientService;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class CaseFour {
    //Check-Out


    @Autowired
    private HotelClientService clientService;
    @Autowired
    private HotelRoomService roomService;
    @Autowired
    private HostingService hostingService;
    @Autowired
    private HotelPersonRepository personRepository;


    public void showCaseFour(){

        Scanner scanner = new Scanner(System.in);
        HotelClient client;
        HotelRoom room;
        LocalDateTime time = LocalDateTime.now();
        Hosting hosting = new Hosting();
        List<HotelPerson> personList = new ArrayList<>();

        System.out.print("Digite o numero do apartamento para check-out: ");
        String roomToCheckout = scanner.nextLine();
        RoomStatus roomStatus = RoomStatus.DISPONIVEL;

        roomService.updateRoomStatus(roomToCheckout, roomStatus);

        System.out.println("Check-out apartamento ");
        hosting = hostingService.findByRoomNumber(roomToCheckout);
        System.out.println(hosting.getClient());
        System.out.println("Escolha a forma de pagamento" +
                "\n 1 - Dinheiro / PIX" +
                "\n 2 - Cartão de crédito" +
                "\n 3 - Cartão de Debito");
        int chosePaymenteMethod = scanner.nextInt();
        if (chosePaymenteMethod == 1){
            System.out.println("Dinheiro / PIX");
            hostingService.hostingTotalPrice(hosting.getBasePrice());
        }
    }
}

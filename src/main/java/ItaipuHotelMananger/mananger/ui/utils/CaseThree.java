package ItaipuHotelMananger.mananger.ui.utils;

import ItaipuHotelMananger.mananger.entities.Hosting;
import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.entities.HotelRoom;
import ItaipuHotelMananger.mananger.services.HostingService;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import ItaipuHotelMananger.mananger.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CaseThree {
    //Hospedar
    //Host

    @Autowired
    private HotelClientService clientService;
    @Autowired
    private HotelRoomService roomService;
    @Autowired
    private HostingService hostingService;

    public void showCaseThree(){
        Scanner scanner = new Scanner(System.in);
        HotelClient client;
        HotelRoom room;
        System.out.print("Digite o CPF do cliente: (somente dígitos) ");
        var buscaCpf = scanner.nextLine();
        try {
            client = clientService.findByCpf(buscaCpf);
            System.out.println("Cliente encontrado: ");
            System.out.println("=========================");
            System.out.println("Numero de pessoas ");
            Integer totalGuests = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Numero do Apartamento.");
            room = roomService.findByRoomNumber(scanner.nextLine());

            Hosting hosting = new Hosting(null, totalGuests, room, client );
            hostingService.saveHosting(hosting);
            System.out.println(hosting);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
}

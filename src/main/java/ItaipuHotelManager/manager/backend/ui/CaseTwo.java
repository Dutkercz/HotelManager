package ItaipuHotelManager.manager.backend.ui;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import ItaipuHotelManager.manager.services.HostingService;
import ItaipuHotelManager.manager.services.HotelClientService;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CaseTwo {
    @Autowired
    private HotelClientService clientService;
    @Autowired
    private HotelRoomService roomService;
    @Autowired
    private HostingService hostingService;

    public void showCaseTwo(){

        Scanner scanner = new Scanner(System.in);


        System.out.println("O que deseja consultar: ");
        System.out.print("""
                1 - Lista de Apartamentos\
                
                2 - Apartamento ocupados.\
                
                3 - Histórico de cliente.\
                
                >>\s""");
        var escolhaCase2 = scanner.nextInt();
        scanner.nextLine();
        if (escolhaCase2 == 1){
            List<HotelRoom> hotelRoomList = roomService.findAll();
            if (hotelRoomList.isEmpty()) {
                System.out.println("Nenhum quarto encontrado.");
            } else {
                System.out.println("Lista de Quartos:");
                hotelRoomList.forEach(System.out::println);
            }
        }
        else if (escolhaCase2 == 2){
            List<HotelRoom> room = roomService.findAll();
            room = room.stream().filter(x -> x.getStatus() == RoomStatus.OCUPADO).toList();
            room.forEach(System.out::println);

        } else if (escolhaCase2 == 3) {
            System.out.println("**** Historico de hospedagem de clientes ****");
            System.out.print("Digite o cpf: ");
            try {
                String cpfToConsulting = scanner.nextLine();
                List<Hosting> hostingList = hostingService.findAllHostings(cpfToConsulting);
                if (hostingList.isEmpty()){
                    System.out.println("**** Nenhuma hospedagem encontrada. ****");
                }
                hostingList.forEach(System.out::println);
                System.out.println();
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }

        }


    }
}

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

        try{
            System.out.print("Digite o CPF do cliente para check-out: ");
            String cpfCheckOut = scanner.nextLine();


            System.out.println("Escolha a forma de pagamento" +
                    "\n 1 - Dinheiro / PIX" +
                    "\n 2 - Cartão de crédito" +
                    "\n 3 - Cartão de Debito");
            String chosePaymentMethod = scanner.nextLine();

            while (!chosePaymentMethod.matches("[1-3]")){
                System.out.print("Escolha uma opção válida." +
                        "\n 1 - Dinheiro / PIX" +
                        "\n 2 - Cartão de crédito" +
                        "\n 3 - Cartão de Debito" +
                        ">>  ");
                chosePaymentMethod = scanner.nextLine();
            }
            System.out.println(hostingService.checkOut(cpfCheckOut, chosePaymentMethod));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

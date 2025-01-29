package ItaipuHotelManager.manager.ui.utils;

import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.services.HotelClientService;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CaseTwo {
    @Autowired
    HotelClientService clientService;

    @Autowired
    HotelRoomService roomService;

    public void showCaseTwo(){

        Scanner scanner = new Scanner(System.in);


        System.out.println("O que deseja consultar: ");
        System.out.println("1 - Lista de Apartamentos" +
                "\n2 - Apartamento ocupados" +
                "\n3 - Voltar");
        var escolhaCase2 = scanner.nextInt();
        scanner.nextLine();
        if (escolhaCase2 == 1){
            List<HotelRoom> hotelRoomList = roomService.listOfRoom();
            if (hotelRoomList.isEmpty()) {
                System.out.println("Nenhum quarto encontrado.");
            } else {
                System.out.println("Lista de Quartos:");
                hotelRoomList.forEach(System.out::println);
            }
        }
        if (escolhaCase2 == 2){
            System.out.println("Em construção");
        }


    }
}

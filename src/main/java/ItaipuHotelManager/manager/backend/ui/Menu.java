package ItaipuHotelManager.manager.backend.ui;

import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.repositories.HotelRoomRepository;
import ItaipuHotelManager.manager.services.HotelClientService;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    @Autowired
    private CaseOne one;

    @Autowired
    private CaseTwo two;

    @Autowired
    private CaseThree three;

    @Autowired
    private CaseFour four;

    @Autowired
    private HotelRoomService roomService;

    @Autowired
    private HotelClientService clientService;

    @Autowired
    private HotelRoomRepository repository;

    public void showMenu(){
        System.out.println();
        System.out.println("===================================");
        System.out.println(" Bem vindo ao Sistema Hotel Itaipu");
        System.out.println("===================================");
        System.out.println("Apartamento disponiveis:");
        List<HotelRoom> livres = roomService.getAvailableRooms();
        livres.forEach(x -> System.out.println("Apartamento: "+ x.getRoomNumber()));
        System.out.println("=====================================================");
        System.out.println("Apartamento ocupados:");
        List<HotelRoom> ocupados = roomService.getOccupiedRooms();
        ocupados.forEach(x -> System.out.println("Apartamento: " + x.getRoomNumber() ));
        System.out.println();
        while (true){
            System.out.print("""
                    Selecione uma opção no menu:\
                    
                    	1 - Cadastro\
                    
                    	2 - Consultas\
                    
                    	3 - Check-in\
                    
                    \t4 - Check-out\
                    
                    \t5 - Sair\
                    
                    \t>>\s""");
            var escolha = scanner.nextLine();
            switch (escolha){
                case "1":
                    one.showCaseOne();
                    break;

                case "2":
                    two.showCaseTwo();
                    break;
                case "3":
                    three.showCaseThree();
                    break;
                case "4":
                    four.showCaseFour();
                    break;
                case "5":
                    System.out.println("Encerrando o sistema...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;

            }
        }
    }
}

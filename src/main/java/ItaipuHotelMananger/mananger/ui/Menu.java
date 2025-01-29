package ItaipuHotelMananger.mananger.ui;

import ItaipuHotelMananger.mananger.repositories.HotelRoomRepository;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import ItaipuHotelMananger.mananger.services.HotelRoomService;
import ItaipuHotelMananger.mananger.ui.utils.CaseOne;
import ItaipuHotelMananger.mananger.ui.utils.CaseThree;
import ItaipuHotelMananger.mananger.ui.utils.CaseTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        System.out.println();
        while (true){
            System.out.println("Selecione uma opção no menu:" +
                    "\n	1 - Cadastro" +
                    "\n	2 - Consultas" +
                    "\n	3 - Hospedgem (Check-in / Check-out)" +
                    "\n\t4 - Sair");
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
                    System.out.println("Encerrando o sistema...");
                    return;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;

            }
        }
    }
}

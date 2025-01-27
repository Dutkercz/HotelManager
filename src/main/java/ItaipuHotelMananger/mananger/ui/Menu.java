package ItaipuHotelMananger.mananger.ui;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.entities.HotelRoom;
import ItaipuHotelMananger.mananger.repositories.HotelRoomRepository;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import ItaipuHotelMananger.mananger.services.HotelRoomService;
import ItaipuHotelMananger.mananger.ui.utils.CaseOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Menu {

    private final Scanner scanner = new Scanner(System.in);


    @Autowired
    CaseOne one;

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
            HotelClient client = new HotelClient();
            System.out.println("Selecione uma opção no menu:" +
                    "\n	1 - Cadastro" +
                    "\n	2 - Consulta" +
                    "\n	3 - Hospedar" +
                    "\n\t4 - Sair");
            var escolha = scanner.nextLine();
            switch (escolha){
                case "1":
                    one.showCaseOne();
                    break;

                case "2":
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
                    break;
                case "3":
                    System.out.print("Digite o CPF do cliente: (somente dígitos) ");
                    var buscaCpf = scanner.nextLine();
                    try {
                        client = clientService.findByCpf(buscaCpf);
                        System.out.println("Cliente encontrado: " + client);
                    } catch (RuntimeException e) {
                        System.out.println(e.getMessage());
                    }
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

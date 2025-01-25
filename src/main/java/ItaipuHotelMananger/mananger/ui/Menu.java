package ItaipuHotelMananger.mananger.ui;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.entities.HotelRoom;
import ItaipuHotelMananger.mananger.repositories.HotelRoomRepository;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import ItaipuHotelMananger.mananger.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    private final Scanner scanner = new Scanner(System.in);
    @Autowired
    private HotelClientService clientService;

    @Autowired
    private HotelRoomService roomService;

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
                    "\n	2 - Consulta" +
                    "\n	3 - Encerrar");
            var escolha = scanner.nextLine();
            switch (escolha){
                case "1":
                    System.out.println(" **** Entre com os seguintes dados. ****");
                    System.out.println("==========================================");
                    System.out.print("Nome completo: ");
                    var name = scanner.nextLine();
                    System.out.print("CPF: ");
                    var cpf = scanner.nextLine();
                    System.out.print("Endereço: ");
                    var address = scanner.nextLine();
                    System.out.print("Cidade: ");
                    var city = scanner.nextLine();
                    System.out.print("Telefone: ");
                    var phone = scanner.nextLine();
                    System.out.print("Email: ");
                    var email = scanner.nextLine();
                    System.out.print("CNPJ: ");
                    var cnpj = scanner.nextLine();
                    clientService.insert(new HotelClient(null, name, cpf, city, address, email, phone, cnpj));
                    System.out.println("*** Cliente cadastrado com sucesso! ***");
                    break;
                case "2":
                    System.out.println("O que deseja consultar: ");
                    System.out.println("1 - Lista de Apartamentos" +
                            "2 - Apartamento ocupados");
                    var escolhaCase2 = scanner.nextInt();
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
                    System.out.println("Encerrando o sistema...");
                    return;

                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;

            }
        }
    }
}

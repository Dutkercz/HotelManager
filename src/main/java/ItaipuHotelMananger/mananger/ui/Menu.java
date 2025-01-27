package ItaipuHotelMananger.mananger.ui;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.entities.HotelRoom;
import ItaipuHotelMananger.mananger.entities.utils.CpfValidation;
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
                    "\n	3 - Hospedar" +
                    "\n 4 - Sair");
            var escolha = scanner.nextLine();
            switch (escolha){
                case "1":
                    System.out.println(" **** Entre com os seguintes dados. ****");
                    System.out.println("==========================================");
                    System.out.print("Nome completo: ");
                    String name = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    boolean validade = CpfValidation.isValidCPF(cpf);
                    while (!validade){
                        System.out.println("Cpf inválido. Digite novamente.");
                        cpf = scanner.nextLine();
                        validade = CpfValidation.isValidCPF(cpf);
                    }
                    System.out.print("Endereço: ");
                    String address = scanner.nextLine();
                    System.out.print("Cidade: ");
                    String city = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();
                    System.out.print("CNPJ: ");
                    String cnpj = scanner.nextLine();
                    try {
                        clientService.insert(new HotelClient(null, name, cpf, city, address, email, phone, cnpj));
                        System.out.println("*** Cliente cadastrado com sucesso! ***");
                    }catch (RuntimeException e){
                        System.out.println("Há informações inválidas, verifique.");
                    }
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
                    System.out.print("Digite o CPF do cliente: ");
                    var buscaCpf = scanner.nextLine();
                    try {
                        HotelClient client = clientService.findByCpf(buscaCpf);
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

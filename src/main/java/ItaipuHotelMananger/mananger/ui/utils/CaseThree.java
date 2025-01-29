package ItaipuHotelMananger.mananger.ui.utils;

import ItaipuHotelMananger.mananger.entities.Hosting;
import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.entities.HotelPerson;
import ItaipuHotelMananger.mananger.entities.HotelRoom;
import ItaipuHotelMananger.mananger.repositories.HotelPersonRepository;
import ItaipuHotelMananger.mananger.services.HostingService;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import ItaipuHotelMananger.mananger.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private HotelPersonRepository personRepository;

    public void showCaseThree(){
        Scanner scanner = new Scanner(System.in);
        HotelClient client;
        HotelRoom room;
        LocalDateTime time = LocalDateTime.now();
        Hosting hosting = new Hosting();
        List<HotelPerson> personList = new ArrayList<>();

        System.out.print("Digite o CPF do cliente: (somente dígitos) ");
        var buscaCpf = scanner.nextLine();
        try {
            client = clientService.findByCpf(buscaCpf);
            System.out.println("**** Cliente encontrado! ****");
            System.out.println("=========================");
            System.out.print("Numero de pessoas: ");
            Integer totalGuests = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Numero de diárias: ");
            int dailyNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Numero do Apartamento: ");
            room = roomService.findByRoomNumber(scanner.nextLine());

//            Double valor;
//            while (true) {
//                System.out.println("Forma de pagamento:" +
//                        "\n1 - Dinheiro/Pix" +
//                        "\n2 - Cartão Crédito" +
//                        "\n3 - Cartão Débito" +
//                        "\n >> ");
//                int escolhaFormaDePagamento = scanner.nextInt();
//                if (escolhaFormaDePagamento == 1){
//                    valor = hostingService.hostingTotalPrice(hosting.getBasePrice());
//                    break;
//                } else if (escolhaFormaDePagamento == 2) {
//                    valor = hostingService.hostingTotalPriceCredit(hosting.getBasePrice());
//                    break;
//                }else if ( escolhaFormaDePagamento == 3) {
//                    valor = hostingService.hostingTotalPriceDebit(hosting.getBasePrice());
//                    break;
//                }else {
//                    System.out.println("Escolha inválida");
//                }
//            }
            if(totalGuests > 1){
                System.out.print("Entre com o nome do(s) demais Hóspedes do Apartamento " + room +">>");

                for (int i = 1; i < totalGuests; i++){
                    personList.add(new HotelPerson(scanner.nextLine()));

                }
                personRepository.saveAll(personList);
            }

            hosting = new Hosting(null, totalGuests, dailyNumber, room, client, time ,
                    time.plusDays(dailyNumber).withHour(12).withMinute(0), personList);
            hostingService.saveHosting(hosting);
            System.out.println("=================================");
            System.out.println(" **** Hospedagem concluida! ****");
            System.out.println("=================================");
            System.out.println("\t**** Resumo: ****");
            System.out.println(hosting);
            System.out.println();
            System.out.println("===============================");


        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
}

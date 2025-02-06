package ItaipuHotelManager.manager.backend.ui;

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
public class CaseThree {
    //Check-In

    @Autowired
    private HotelClientService clientService;
    @Autowired
    private HotelRoomService roomService;
    @Autowired
    private HostingService hostingService;
    @Autowired
    private HotelPersonRepository personRepository;
    @Autowired
    private CaseOne one;

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
            if (client != null) {
                System.out.println("**** Cliente encontrado! ****");
                System.out.println("=========================");
                System.out.print("Numero de pessoas: ");
                int totalGuests = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Numero de diárias: ");
                int dailyNumber = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Numero do Apartamento: ");
                room = roomService.findByRoomNumber(scanner.nextLine().strip());
                while(true){
                    if (room.getStatus() == RoomStatus.OCUPADO){
                        System.out.println("Apartamento ocupado, escolha outro.");
                        room = roomService.findByRoomNumber(scanner.nextLine().strip());
                    }else {
                        break;
                    }
                }
                if (totalGuests > 1) {
                    System.out.print("Entre com o nome do(s) demais Hóspedes do Apartamento " + room);

                    for (int i = 1; i < totalGuests; i++) {
                        System.out.print(">> ");
                        HotelPerson person = new HotelPerson(scanner.nextLine());
                        personRepository.save(person);
                        personRepository.save(person);
                        personList.add(person);

                    }
                    personRepository.saveAll(personList);
                }

                RoomStatus status = RoomStatus.OCUPADO;
                roomService.updateRoomStatus(room.getRoomNumber(), client);

//                hostingService.checkIn(totalGuests, dailyNumber, room, client, time, personList, status);
//                hosting = new Hosting(null, totalGuests, dailyNumber, room, client, time,
//                        time.plusDays(dailyNumber).withHour(12).withMinute(0), personList, status);

                System.out.println("=================================");
                System.out.println(" **** Hospedagem concluida! ****");
                System.out.println("=================================");
                System.out.println("\t**** Resumo: ****");
                System.out.println(hosting);
                System.out.println();
                System.out.println("===============================");

            }else {
                System.out.println("Cliente não encontrado, faça o cadastro primeiro.");
                System.out.println("Deseja cadastrar? ");
                System.out.print("1 - Sim\n" +
                        "2 - Não" +
                        "\n >> ");
                int escolhaCasdastrar = scanner.nextInt();
                if(escolhaCasdastrar == 1){
                    one.showCaseOne();
                }else {
                    System.out.println("Voltando ao menu principal...");
                    Thread.sleep(2000);
                }
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

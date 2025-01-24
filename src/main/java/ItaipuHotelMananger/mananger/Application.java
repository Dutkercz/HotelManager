package ItaipuHotelMananger.mananger;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.entities.HotelRoom;
import ItaipuHotelMananger.mananger.repositories.HotelClientRepository;
import ItaipuHotelMananger.mananger.repositories.HotelRoomRepository;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import ItaipuHotelMananger.mananger.services.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@SpringBootApplication
@Component
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Autowired
	private HotelClientService service;
	@Autowired
	HotelRoomService roomService;
	@Autowired
	HotelRoomRepository repository;

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);


		roomService.saveRoom(repository.save(new HotelRoom(null, "01", 1, 1)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "02", 1, 0)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "03", 1, 1)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "04", 1, 2)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "05", 0, 2)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "06", 0, 2)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "07", 1, 2)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "08", 1, 1)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "09", 0, 2)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "10", 0, 2)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "11", 0, 2)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "12", 0, 2)));
		roomService.saveRoom(repository.save(new HotelRoom(null, "13", 0, 3)));


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
					service.insert(new HotelClient(null, name, cpf, city, address, email, phone, cnpj));
				case "2":
				case "3":


			}
		}
	}
}

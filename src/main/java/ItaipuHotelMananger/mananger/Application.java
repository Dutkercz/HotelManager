package ItaipuHotelMananger.mananger;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.repositories.HotelClientRepository;
import ItaipuHotelMananger.mananger.services.HotelClientService;
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

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);


		System.out.println("Bem vindo ao Sistema Hotel Itaipu");
		System.out.println();
		String cont = "";
		while (true){
			System.out.println("Selecione uma opção no menu:" +
					"\n	1 - Cadastro" +
					"\n	2 - Consulta" +
					"\n 3 - Encerrar");
			var escolha = scanner.nextDouble();
			if(escolha == 1){
				scanner.nextLine();
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


			}
		}
	}
}

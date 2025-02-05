package ItaipuHotelManager.manager.backend.ui;

import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.utils.CpfValidation;
import ItaipuHotelManager.manager.services.HotelClientService;
import org.apache.commons.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class CaseOne {
    // Opção 1 no menu -> CADASTRO DE CLIENTE;
    // Option 1 in menu -> CLIENT REGISTRATION;

    @Autowired
    HotelClientService clientService;

    public void showCaseOne() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=**** Entre com os seguintes dados ****=");
        System.out.println("==========================================");
        while (true) {
            System.out.print("CPF: (somente dígitos) ");
            String cpf = scanner.nextLine();

            //Verifica se o CPF é válido.
            //Check if the registration is valid.
            while (!CpfValidation.isValidCPF(cpf)) {
                System.out.println(" **** CPF inválido. Digite novamente. ****");
                cpf = scanner.nextLine();
            }

            try{
                //Verifica se o cliente já possui cadastro;
                //Check if client already has a registration;
                if (clientService.findByCpf(cpf).getCpf().equals(cpf)) {
                    System.out.println("==========================================\n" +
                            "     **** Cliente já cadastrado. **** " +
                            "\n==========================================\n");
                    break;
                }
            } catch (RuntimeException e) {
                System.out.print("Nome completo: ");
                String name = WordUtils.capitalizeFully(scanner.nextLine()).strip();
                System.out.print("Endereço com número: ");
                String address = WordUtils.capitalizeFully(scanner.nextLine());
                System.out.print("Cidade: ");
                String city = WordUtils.capitalizeFully(scanner.nextLine());
                System.out.print("Telefone: ");
                String phone = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine().toLowerCase();
                System.out.print("CNPJ: ");
                String cnpj = scanner.nextLine();

                try {
                    // Inserir novo cliente no banco;
                    // Insert new client in database;
                    clientService.insert(new HotelClient(null, name, cpf, city, address, email, phone, cnpj));
                    System.out.println("**** Cliente cadastrado com sucesso! ****");
                    break;
                } catch (RuntimeException ex) {
                    System.out.println("Erro ao cadastrar o cliente. Verifique as informações e tente novamente.");
                }
            }
        }
    }
}

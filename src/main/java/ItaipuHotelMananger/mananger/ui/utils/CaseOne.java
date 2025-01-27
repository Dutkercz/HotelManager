package ItaipuHotelMananger.mananger.ui.utils;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.entities.utils.CpfValidation;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;


@Component
public class CaseOne {

    @Autowired
    HotelClientService clientService;

    public void showCaseOne() {
        HotelClient client;
        Scanner scanner = new Scanner(System.in);

        System.out.println(" **** Entre com os seguintes dados. ****");
        System.out.println("==========================================");
        while (true) {
            System.out.print("CPF: (somente dígitos) ");
            String cpf = scanner.nextLine();

            // Verificar validade do CPF
            while (!CpfValidation.isValidCPF(cpf)) {
                System.out.println("CPF inválido. Digite novamente.");
                cpf = scanner.nextLine();
            }

            try{
                if (clientService.findByCpf(cpf).getCpf().equals(cpf)) {
                    System.out.println("Cliente já cadastrado: ");
                    break; // Sai do loop caso o cliente seja encontrado
                }
            } catch (RuntimeException e) {
                System.out.print("Nome completo: ");
                String name = scanner.nextLine();
                System.out.print("Endereço com número: ");
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
                    // Inserir novo cliente no banco
                    clientService.insert(new HotelClient(null, name, cpf, city, address, email, phone, cnpj));
                    System.out.println("*** Cliente cadastrado com sucesso! ***");
                    break; // Sai do loop após cadastro
                } catch (RuntimeException ex) {
                    System.out.println("Erro ao cadastrar o cliente. Verifique as informações e tente novamente.");
                }
            }
        }
    }
}

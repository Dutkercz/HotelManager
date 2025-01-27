package ItaipuHotelMananger.mananger.ui.utils;

import ItaipuHotelMananger.mananger.entities.HotelClient;
import ItaipuHotelMananger.mananger.entities.utils.CpfValidation;
import ItaipuHotelMananger.mananger.services.HotelClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Scanner;


@Component
public class CaseOne {

    @Autowired
    HotelClientService clientService;

    public void showCaseOne(){
        HotelClient client;
        Scanner scanner = new Scanner(System.in);

        System.out.println(" **** Entre com os seguintes dados. ****");
        System.out.println("==========================================");
        System.out.print("CPF: (somente dígitos) ");
        String cpf = scanner.nextLine();
        while (true) {
            //verificar validade do cpf. // Check if the register is valid.
            boolean validade = CpfValidation.isValidCPF(cpf);
            while (!validade) {
                System.out.println("Cpf inválido. Digite novamente.");
                cpf = scanner.nextLine();
                validade = CpfValidation.isValidCPF(cpf);
            }
            try {// Tentar encontrar o cliente pelo CPF // try to find client by registration

                client = clientService.findByCpf(cpf);
                System.out.println("Cliente encontrado: " + client);
                break; // Sai do loop se o cliente for encontrado // leave of the loop if guest have one.
            } catch (RuntimeException e){
                System.out.println(" **** Cliente não encontrado. ****");
            }
            System.out.print("Deseja realizar o cadastro?" +
                    "\n[S] - Para continuar" +
                    "\n[N] - Para sair" +
                    "\n>> ");
            char continuarCaseOne = scanner.nextLine().toUpperCase().charAt(0);

            if (continuarCaseOne == 'S') {
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
                    clientService.insert(new HotelClient(null, name, cpf, city, address, email, phone, cnpj));
                    System.out.println("*** Cliente cadastrado com sucesso! ***");
                    break;
                }catch (RuntimeException e){
                    System.out.println("Há informações inválidas, verifique.");
                }
            }
            else {break;}
        }
    }
}


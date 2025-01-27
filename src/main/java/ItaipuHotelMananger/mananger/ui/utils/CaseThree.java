package ItaipuHotelMananger.mananger.ui.utils;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CaseThree {


    public void showCaseThree(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o CPF do cliente: (somente dígitos) ");
        var buscaCpf = scanner.nextLine();
        try {
            client = clientService.findByCpf(buscaCpf);
            System.out.println("Cliente encontrado: " + client);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }
}

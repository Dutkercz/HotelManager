package ItaipuHotelManager.manager.entities.utils;

import org.springframework.stereotype.Component;

@Component
public class CpfValidation {

    public static boolean isValidCPF(String cpf) {
        // Remove espaços em branco
        cpf = cpf.trim();

        // Verifica se contém "." ou "-"
        if (cpf.contains(".") || cpf.contains("-")) {
            return false;
        }

        // Verifica se tem exatamente 11 dígitos numéricos
        if (!cpf.matches("\\d{11}")) {
            return false;
        }

        // Verifica se todos os números são iguais (ex: 11111111111 -> inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Validação do dígito verificador
        return validateDigits(cpf);
    }

    private static boolean validateDigits(String cpf) {
        int sum = 0;
        int weight = 10;

        // Calcula o primeiro dígito verificador
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }

        int firstDigit = (sum * 10) % 11;
        if (firstDigit == 10) firstDigit = 0;
        if (firstDigit != (cpf.charAt(9) - '0')) return false;

        // Calcula o segundo dígito verificador
        sum = 0;
        weight = 11;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }

        int secondDigit = (sum * 10) % 11;
        if (secondDigit == 10) secondDigit = 0;

        return secondDigit == (cpf.charAt(10) - '0');
    }
}
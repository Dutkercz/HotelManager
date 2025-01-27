package ItaipuHotelMananger.mananger.entities.utils;

import org.springframework.stereotype.Component;

@Component
public class CpfValidation {

    public static boolean isValidCPF(String cpf) {
        // Remove non-numeric characters // Removendo caracteres não numéricos.
        cpf = cpf.replaceAll("\\D", "");

        // Check length and invalid sequences // Check no tamanho da sequência e se ela é válida.
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum1 = 0, sum2 = 0;
            for (int i = 0; i < 9; i++) {
                int digit = Character.getNumericValue(cpf.charAt(i));
                sum1 += digit * (10 - i);
                sum2 += digit * (11 - i);
            }

            // Calculate verification digits / Calcular dígitos verificadores.
            int dv1 = (sum1 * 10) % 11;
            dv1 = (dv1 == 10) ? 0 : dv1;

            sum2 += dv1 * 2;
            int dv2 = (sum2 * 10) % 11;
            dv2 = (dv2 == 10) ? 0 : dv2;

            // Compare verification digits // comprando digitos verificadores.
            return dv1 == Character.getNumericValue(cpf.charAt(9))
                    && dv2 == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }
}

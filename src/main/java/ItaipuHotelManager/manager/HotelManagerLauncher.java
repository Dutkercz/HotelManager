package ItaipuHotelManager.manager;

import ItaipuHotelManager.manager.frontend.HotelManagementUI;

import javax.swing.*;

public class HotelManagerLauncher {
    public static void main(String[] args) {
        new Thread(() -> Application.main(new String[]{})).start();

        // Aguarda um tempo para o backend subir
        try { Thread.sleep(1000); } catch (InterruptedException e) { }

        // Inicia a interface gráfica
        SwingUtilities.invokeLater(HotelManagementUI::new);
    }
}

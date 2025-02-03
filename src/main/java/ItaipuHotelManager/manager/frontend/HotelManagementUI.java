package ItaipuHotelManager.manager.frontend;

import javax.swing.*;
import java.awt.*;

public class HotelManagementUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelManagementUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Gerenciamento Hotel Itaipu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 1));

        JButton btnClientes = new JButton("Gerenciar Clientes");
        JButton gerenciarApartamentos = new JButton("Gerenciar Apartamentos");
        JButton gerenciarHospedagens = new JButton("Gerenciar Hospedagens");

        btnClientes.addActionListener(e -> btnClientes.addActionListener(f -> new ClientUi()));
        gerenciarApartamentos.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Abrir tela de apartamento"));
        gerenciarHospedagens.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Abrir tela de hospedagens"));

        frame.add(btnClientes);
        frame.add(gerenciarApartamentos);
        frame.add(gerenciarHospedagens);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

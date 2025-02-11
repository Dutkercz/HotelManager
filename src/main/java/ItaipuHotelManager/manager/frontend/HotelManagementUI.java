package ItaipuHotelManager.manager.frontend;

import ItaipuHotelManager.manager.frontend.checkin.CheckInUi;
import ItaipuHotelManager.manager.frontend.checkout.CheckOutUi;
import ItaipuHotelManager.manager.frontend.client.management.ClientUi;
import ItaipuHotelManager.manager.frontend.room.management.RoomsUi;

import javax.swing.*;
import java.awt.*;

public class HotelManagementUI {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(HotelManagementUI::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Gerenciamento Hotel Itaipu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        ImageIcon iconButton = new ImageIcon(); // Você pode adicionar um ícone aqui

        JButton btnClientes = new JButton("Gerenciar Clientes", iconButton);
        JButton btnApartamentos = new JButton("Gerenciar Apartamentos", iconButton);
        JButton btnCheckIn = new JButton("Check-In", iconButton);
        JButton btnCheckOut = new JButton("Check-Out", iconButton);

        btnClientes.setBackground(new Color(2, 36 , 52));
        btnClientes.setForeground(Color.white);
        btnApartamentos.setBackground(new Color(2, 36 , 52));
        btnApartamentos.setForeground(Color.white);
        btnCheckIn.setBackground(new Color(2, 36 , 52));
        btnCheckIn.setForeground(Color.white);
        btnCheckOut.setBackground(new Color(2, 36 , 52));
        btnCheckOut.setForeground(Color.white);

        btnClientes.addActionListener(e -> {
            SwingUtilities.invokeLater(ClientUi::new);
        });

        btnApartamentos.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new RoomsUi(frame));
        });

        btnCheckIn.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new CheckInUi(frame));
        });
        btnCheckOut.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new CheckOutUi(frame));
        });

        panel.add(btnClientes);
        panel.add(btnApartamentos);
        panel.add(btnCheckIn);
        panel.add(btnCheckOut);

        frame.add(panel, BorderLayout.CENTER);

        JLabel logo = new JLabel(new ImageIcon("C:\\Users\\Dell\\Downloads\\hi.png"));
        frame.add(logo, BorderLayout.NORTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

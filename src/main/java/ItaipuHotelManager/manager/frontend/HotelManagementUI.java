package ItaipuHotelManager.manager.frontend;

import ItaipuHotelManager.manager.frontend.client.management.ClientUi;
import ItaipuHotelManager.manager.frontend.room.management.RoomsUi;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;

public class HotelManagementUI {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext("ItaipuHotelManager.manager");

        SwingUtilities.invokeLater(() -> createAndShowGUI(context));
    }

    private static void createAndShowGUI(ApplicationContext context) {
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

        btnClientes.setBackground(new Color(42, 60, 72));
        btnClientes.setForeground(Color.white);
        btnApartamentos.setBackground(new Color(42, 60, 72));
        btnApartamentos.setForeground(Color.white);
        btnCheckIn.setBackground(new Color(42, 60, 72));
        btnCheckIn.setForeground(Color.white);
        btnCheckOut.setBackground(new Color(42, 60, 72));
        btnCheckOut.setForeground(Color.white);

        btnClientes.addActionListener(e -> {
            SwingUtilities.invokeLater(ClientUi::new); // Executa na thread do Swing
        });

        btnApartamentos.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> new RoomsUi(context.getBean(HotelRoomService.class)));
        });

        panel.add(btnClientes);
        panel.add(btnApartamentos);
        panel.add(btnCheckIn);
        panel.add(btnCheckOut);

        frame.add(panel, BorderLayout.CENTER);

        JLabel logo = new JLabel(new ImageIcon("C:\\Users\\cris_\\Desktop\\java\\hi.png"));
        frame.add(logo, BorderLayout.NORTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

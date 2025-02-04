package ItaipuHotelManager.manager.frontend;

import ItaipuHotelManager.manager.services.HotelRoomService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.*;

public class HotelManagementUI {
    public static void main(String[] args) {
        // Inicializa o Spring Context
        ApplicationContext context = new AnnotationConfigApplicationContext("ItaipuHotelManager.manager");

        // Chama o método para criar a interface gráfica
        SwingUtilities.invokeLater(() -> createAndShowGUI(context));
    }

    private static void createAndShowGUI(ApplicationContext context) {
        // Configura a janela principal
        JFrame frame = new JFrame("Gerenciamento Hotel Itaipu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 650);
        frame.setLocationRelativeTo(null);

        // Cria o painel de botões
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 10, 10));

        ImageIcon iconButton = new ImageIcon(); // Você pode adicionar um ícone aqui

        // Botões de navegação
        JButton btnClientes = new JButton("Gerenciar Clientes", iconButton);
        JButton btnApartamentos = new JButton("Gerenciar Apartamentos", iconButton);
        JButton btnCheckIn = new JButton("Check-In", iconButton);
        JButton btnCheckOut = new JButton("Check-Out", iconButton);

        // Estilo dos botões
        btnClientes.setBackground(new Color(42, 60, 72));
        btnClientes.setForeground(Color.white);
        btnApartamentos.setBackground(new Color(42, 60, 72));
        btnApartamentos.setForeground(Color.white);
        btnCheckIn.setBackground(new Color(42, 60, 72));
        btnCheckIn.setForeground(Color.white);
        btnCheckOut.setBackground(new Color(42, 60, 72));
        btnCheckOut.setForeground(Color.white);

        // Ação para o botão "Gerenciar Clientes"
        btnClientes.addActionListener(e -> {
            // Criar a interface de clientes
            SwingUtilities.invokeLater(ClientUi::new); // Executa na thread do Swing
        });

        // Ação para o botão "Gerenciar Apartamentos"
        btnApartamentos.addActionListener(e -> {
            // Criar a interface de apartamentos
            SwingUtilities.invokeLater(() -> new RoomsUi(context.getBean(HotelRoomService.class)));
        });

        // Adiciona os botões ao painel
        panel.add(btnClientes);
        panel.add(btnApartamentos);
        panel.add(btnCheckIn);
        panel.add(btnCheckOut);

        // Exibe a janela
        frame.add(panel, BorderLayout.CENTER);

        // Logo
        JLabel logo = new JLabel(new ImageIcon("C:\\Users\\cris_\\Desktop\\java\\hi.png"));
        frame.add(logo, BorderLayout.NORTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

package ItaipuHotelManager.manager.frontend;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import org.springframework.web.client.RestTemplate;

public class ClientUi {
    private JFrame frame;
    private JPanel panel;
    private JButton btnCarregar;
    private JTextArea textArea;

    public ClientUi() {
        frame = new JFrame("Clientes");
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        btnCarregar = new JButton("Carregar Clientes");
        textArea = new JTextArea(10, 30);
        textArea.setEditable(false);

        btnCarregar.addActionListener(e -> carregarClientes());

        panel.add(btnCarregar, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void carregarClientes() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/clients";

        try {
            String response = restTemplate.getForObject(url, String.class);
            textArea.setText(response);
        } catch (Exception e) {
            textArea.setText("Erro ao buscar clientes.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientUi::new);
    }
}

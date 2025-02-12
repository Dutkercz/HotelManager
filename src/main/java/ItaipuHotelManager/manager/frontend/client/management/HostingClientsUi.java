package ItaipuHotelManager.manager.frontend.client.management;

import ItaipuHotelManager.manager.entities.Hosting;
import org.hibernate.Hibernate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HostingClientsUi {
    private final JFrame frame;
    private final DefaultTableModel tableModel;
    private final JTextField txtCpf;

    public HostingClientsUi() {
        frame = new JFrame("Hospedagens do Cliente");
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("CPF do Cliente:"));

        txtCpf = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(42, 60, 72));
        btnBuscar.setForeground(Color.white);

        panel.add(txtCpf);
        panel.add(btnBuscar);
        frame.add(panel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Nome", "Apartamento", "Data Check-in", "Data Check-out", "Valor Total"}, 0);
        JTable table = new JTable(tableModel);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> carregarHospedagens());

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void carregarHospedagens() {
        String cpf = txtCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Digite um CPF!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/allhosting/" + cpf + "/all";

        try {
            ResponseEntity<List<Hosting>> response = restTemplate.exchange(url, HttpMethod.GET,
                    null, new ParameterizedTypeReference<List<Hosting>>() {
                    });

            List<Hosting> hosting = response.getBody();
            assert hosting != null;
            tableModel.setRowCount(0);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String checkInString;
            String checkOutString;
            if (!hosting.isEmpty()) {
                for (Hosting h : hosting) {
                    Hibernate.initialize(h.getClient());
                    checkInString = dtf.format(h.getCheckIn());
                    checkOutString = dtf.format(h.getCheckOut());
                    tableModel.addRow(new Object[]{
                            h.getClient().getFullName(),
                            h.getRoom().getRoomNumber(),
                            checkInString,
                            checkOutString,
                            h.getTotalPrice()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Nenhuma hospedagem encontrada para este CPF.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erro ao buscar hospedagens: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

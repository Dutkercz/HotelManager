package ItaipuHotelManager.manager.frontend.checkout;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelRoom;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CheckOutUi {
    private final JDialog dialog;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private HotelRoom selectedRoom;
    private Hosting selectedHosting;
    private final JComboBox<String> paymentMethod;
    private final JLabel lblTotalAmount;

    public CheckOutUi(JFrame parent) {
        dialog = new JDialog(parent, "Realizar Check-out", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(850, 500);
        dialog.setLocationRelativeTo(parent);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Selecione um apartamento ocupado:"));

        tableModel = new DefaultTableModel(new Object[]{"Número", "Hóspede"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(new JLabel("Método de Pagamento:"));
        paymentMethod = new JComboBox<>(new String[]{"Crédito", "Débito", "Dinheiro/Pix"});
        bottomPanel.add(paymentMethod);

        lblTotalAmount = new JLabel("Total: R$0,00");
        bottomPanel.add(lblTotalAmount);

        JButton btnConfirm = new JButton("Confirmar Check-out");
        //btnConfirm.addActionListener(this::confirmarCheckOut);
        bottomPanel.add(btnConfirm);

        dialog.add(bottomPanel, BorderLayout.SOUTH);

        carregarHospedagensOcupadas();
        table.getSelectionModel().addListSelectionListener(e -> selectRoom());

        dialog.setVisible(true);
    }
    private void carregarHospedagensOcupadas() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rooms/occupied";
        ResponseEntity<List<HotelRoom>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelRoom>>() {});
        List<HotelRoom> occupiedRooms = response.getBody();
        tableModel.setRowCount(0);
        assert occupiedRooms != null;
        for (HotelRoom h : occupiedRooms){
            tableModel.addRow(new Object[]{h.getRoomNumber(), h.getClient().getFullName()});
        }
    }
    private void selectRoom() {

    }
    private void calcularValorTotal() {

    }
    private void confirmarCheckOut(ActionEvent e) {

    }



}
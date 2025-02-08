package ItaipuHotelManager.manager.frontend.checkout;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelRoom;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
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
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(parent);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Selecione um apartamento ocupado:"));

        tableModel = new DefaultTableModel(new Object[]{"Apartamento Nº", "Hóspede", "Num. de Hospedes",
                "Total de Diárias", "Sub Total"}, 0);
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
        btnConfirm.addActionListener(this::confirmCheckOut);
        bottomPanel.add(btnConfirm);

        dialog.add(bottomPanel, BorderLayout.SOUTH);

        loadHostingByOccupiedStatus();
        table.getSelectionModel().addListSelectionListener(e -> selectRoom());

        paymentMethod.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedHosting != null) {
                    calculateTotalValue();
                }
            }
        });

        dialog.setVisible(true);
    }
    private void loadHostingByOccupiedStatus() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/allhosting/active";
        ResponseEntity<List<Hosting>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Hosting>>() {});
        List<Hosting> hostingList = response.getBody();
        assert hostingList != null;
        hostingList.sort(Comparator.comparing(x -> x.getRoom().getRoomNumber()));
        tableModel.setRowCount(0);
        for (Hosting h : hostingList){
            tableModel.addRow(new Object[]{h.getRoom().getRoomNumber(),
                    h.getClient().getFullName(), h.getTotalGuest(), h.getDailyNumber(), h.getBasePrice()});
        }
    }
    private void selectRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1){
            String roomNumber = (String) tableModel.getValueAt(selectedRow, 0);
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/allhosting/room/"+roomNumber;
            ResponseEntity<Hosting> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Hosting>() {});
            selectedHosting = response.getBody();
            assert selectedHosting != null;
            selectedRoom = selectedHosting.getRoom();
            calculateTotalValue();
        }

    }
    private void calculateTotalValue() {
        String paymentMethodSelectedItem = (String) paymentMethod.getSelectedItem();
        assert paymentMethodSelectedItem != null;
        if (paymentMethodSelectedItem.equals("Crédito")){
            selectedHosting.setTotalPrice(selectedHosting.getBasePrice()*1.05);
        } else if (paymentMethodSelectedItem.equals("Débito")) {
            selectedHosting.setTotalPrice(selectedHosting.getBasePrice()*1.03);
        }else {
            selectedHosting.setTotalPrice(selectedHosting.getBasePrice());
        }
        double total = selectedHosting.getTotalPrice();
        lblTotalAmount.setText("Total: R$" + String.format("%.2f", total));
    }
    private void confirmCheckOut(ActionEvent e) {
        if (selectedRoom != null && selectedHosting != null) {

            try {
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:8080/allhosting/checkout";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<Hosting> request = new HttpEntity<>(selectedHosting, headers);
                ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, request, Void.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    JOptionPane.showMessageDialog(dialog, "Check-out realizado com sucesso!", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                }
            }catch (HttpServerErrorException ee){
                JOptionPane.showMessageDialog(dialog, "Erro ao realizar check-out. Verifique o servidor.", "Erro!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

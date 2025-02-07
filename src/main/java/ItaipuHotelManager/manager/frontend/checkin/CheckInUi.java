package ItaipuHotelManager.manager.frontend.checkin;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import ItaipuHotelManager.manager.services.HostingService;
import ItaipuHotelManager.manager.services.HotelClientService;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.hibernate.Hibernate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckInUi {
    private JDialog dialog;
    private JTextField txtCpf;
    private JButton btnSearch, btnConfirm;
    private JTable table;
    private DefaultTableModel tableModel;
    private HotelClient selectedClient;
    private HotelRoom selectedRoom;
    private HotelRoomService roomService;
    private HostingService hostingService;
    private JLabel lblClientName;

    public CheckInUi(JFrame parent, HotelRoomService roomService, HotelClientService clientService, HostingService hostingService) {
        this.roomService = roomService;
        this.hostingService = hostingService;

        dialog = new JDialog(parent, "Realizar Check-in", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(700, 580);
        dialog.setLocationRelativeTo(parent);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("CPF do Cliente:"));
        txtCpf = new JTextField(15);
        btnSearch = new JButton("Buscar");
        lblClientName = new JLabel("Nome do Cliente: ");
        topPanel.add(txtCpf);
        topPanel.add(btnSearch);

        dialog.add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Número", "Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        btnConfirm = new JButton("Confirmar Check-in");
        btnConfirm.setEnabled(false);
        dialog.add(btnConfirm, BorderLayout.SOUTH);

        btnSearch.addActionListener(this::buscarCliente);
        table.getSelectionModel().addListSelectionListener(e -> selectRoom());
        btnConfirm.addActionListener(e -> confirmarCheckIn());

        dialog.setVisible(true);
    }

    private void buscarCliente(ActionEvent e) {
        String cpf = txtCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Digite um CPF.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/clients/" + cpf;
        ResponseEntity<HotelClient> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<HotelClient>() {
        });

        selectedClient = response.getBody();
        tableModel.setRowCount(0);

        if (selectedClient == null) {
            JOptionPane.showMessageDialog(dialog, "Cliente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Hibernate.initialize(selectedClient);
        carregarApartamentosDisponiveis();
    }

    private void carregarApartamentosDisponiveis() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rooms/available";
        ResponseEntity<List<HotelRoom>> getAvailableRooms = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelRoom>>() {
        });
        List<HotelRoom> availableRooms = getAvailableRooms.getBody();

        tableModel.setRowCount(0);

        assert availableRooms != null;
        for (HotelRoom room : availableRooms) {
            tableModel.addRow(new Object[]{room.getRoomNumber(), room.getStatus()});
        }
    }

    private void selectRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String roomNumber = (String) tableModel.getValueAt(selectedRow, 0);

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/rooms/" + roomNumber;

            ResponseEntity<HotelRoom> getRoom = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<HotelRoom>() {
            });
            selectedRoom = getRoom.getBody();
            btnConfirm.setEnabled(true);
        }
    }

    private void confirmarCheckIn() {
        if (selectedClient != null && selectedRoom != null) {

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/hosting/checkin";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            int totalGuest = 1;
            int dailyNumber = 1;
            LocalDateTime checkIn = LocalDateTime.now();
            LocalDateTime checkOut = checkIn.plusDays(dailyNumber);

            Hosting newHosting = new Hosting(
                    null,
                    totalGuest,
                    dailyNumber,
                    selectedRoom,
                    selectedClient,
                    checkIn,
                    checkOut,
                    new ArrayList<>(),
                    RoomStatus.OCUPADO
            );
            HttpEntity<Hosting> request = new HttpEntity<>(newHosting, headers);
            ResponseEntity<Hosting> response = restTemplate.exchange(url, HttpMethod.POST, request, Hosting.class);

            //hostingService.checkIn(newHosting, selectedClient, selectedRoom);

            if (response.getStatusCode().is2xxSuccessful()) {
                JOptionPane.showMessageDialog(dialog, "Check-in realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Erro ao realizar check-in.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
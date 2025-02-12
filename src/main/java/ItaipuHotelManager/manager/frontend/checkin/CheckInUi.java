package ItaipuHotelManager.manager.frontend.checkin;

import ItaipuHotelManager.manager.entities.Hosting;
import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelPerson;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.entities.utils.RoomStatus;
import org.hibernate.Hibernate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckInUi {
    private final JDialog dialog;
    private final JTextField txtCpf;
    private final JButton btnConfirm;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JLabel lblClientName;
    private HotelClient selectedClient;
    private HotelRoom selectedRoom;
    private JSpinner spinnerNumberDaily;
    private JSpinner spinnerNumberPeClients;
    private JTextArea txtAdditionalClients;

    public CheckInUi(JFrame parent) {
        dialog = new JDialog(parent, "Realizar Check-in", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(parent);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("CPF do Cliente:"));
        txtCpf = new JTextField(15);
        JButton btnSearch = new JButton("Buscar");
        lblClientName = new JLabel("Nome do Cliente: ");

        topPanel.add(txtCpf);
        topPanel.add(btnSearch);
        topPanel.add(lblClientName);
        dialog.add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Número", "Status", "Camas Casal", "Camas Solteiro"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.SOUTH);
        setupAdditionalFields();

        btnConfirm = new JButton("Confirmar Check-in");
        btnConfirm.setEnabled(false);
        dialog.add(btnConfirm, BorderLayout.EAST);

        btnSearch.addActionListener(this::searchClients);
        table.getSelectionModel().addListSelectionListener(e -> selectRoom());
        btnConfirm.addActionListener(e -> confirmCheckIn());

        dialog.setVisible(true);
    }

    private void setupAdditionalFields() {
        JPanel extraPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        spinnerNumberDaily = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        spinnerNumberPeClients = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        txtAdditionalClients = new JTextArea(3, 20);
        txtAdditionalClients.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        extraPanel.add(new JLabel("Número de Diárias:"));
        extraPanel.add(spinnerNumberDaily);
        extraPanel.add(new JLabel("Número de Pessoas:"));
        extraPanel.add(spinnerNumberPeClients);
        extraPanel.add(new JLabel("Nome dos hóspedes adicionais (se houver):"));
        extraPanel.add(new JScrollPane(txtAdditionalClients));

        dialog.add(extraPanel, BorderLayout.CENTER);
    }

    private void searchClients(ActionEvent e) {
        String cpf = txtCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Digite um CPF.", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/clients/" + cpf;
        ResponseEntity<HotelClient> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<HotelClient>() {
        });

        selectedClient = response.getBody();
        tableModel.setRowCount(0);

        if (selectedClient == null) {
            JOptionPane.showMessageDialog(dialog, "Cliente não encontrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Hibernate.initialize(selectedClient);
        loadAvailableRooms();
        lblClientName.setText("Nome do Cliente: " + selectedClient.getFullName());
    }

    private void loadAvailableRooms() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rooms/available";
        ResponseEntity<List<HotelRoom>> getAvailableRooms = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelRoom>>() {
        });
        List<HotelRoom> availableRooms = getAvailableRooms.getBody();

        tableModel.setRowCount(0);
        if (availableRooms != null) {
            for (HotelRoom room : availableRooms) {
                tableModel.addRow(new Object[]{room.getRoomNumber(), room.getStatus(),
                        room.getDoubleBeds(), room.getSingleBeds()});
            }
        }
    }

    private void selectRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String roomNumber = (String) tableModel.getValueAt(selectedRow, 0);

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/rooms/" + roomNumber;

            ResponseEntity<HotelRoom> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<HotelRoom>() {
            });
            selectedRoom = response.getBody();
            assert selectedRoom != null;
            selectedRoom.setClient(null);
            btnConfirm.setEnabled(true);
        }
    }

    private void confirmCheckIn() {
        if (selectedClient != null && selectedRoom != null) {
            HotelClient client = selectedClient;
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/allhosting/checkin";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            int numDiarias = (int) spinnerNumberDaily.getValue();
            int numPessoas = (int) spinnerNumberPeClients.getValue();
            String nomesAdicionaisStr = txtAdditionalClients.getText().trim();

            List<HotelPerson> personsList = new ArrayList<>();

            if (!nomesAdicionaisStr.isEmpty()) {
                String[] nomesAdicionais = nomesAdicionaisStr.split("\n");
                for (String names : nomesAdicionais) {
                    personsList.add(new HotelPerson(names.trim()));
                }
            }
            if (personsList.size() + 1 != numPessoas) {
                JOptionPane.showMessageDialog(dialog,
                        "Número de pessoas não corresponde à quantidade informada!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime checkIn = LocalDateTime.now();
            LocalDateTime checkOut = checkIn.plusDays(numDiarias);

            Hosting newHosting = new Hosting(null, numPessoas, numDiarias, selectedRoom, client,
                    checkIn, checkOut, personsList, RoomStatus.OCUPADO);

            try {
                HttpEntity<Hosting> request = new HttpEntity<>(newHosting, headers);
                ResponseEntity<Hosting> response = restTemplate.exchange(url, HttpMethod.POST, request, Hosting.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    JOptionPane.showMessageDialog(dialog, "Check-in realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Erro ao realizar check-in.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (HttpServerErrorException e) {
                JOptionPane.showMessageDialog(dialog, "Verifique o servidor ou se há uma hospedagem ativa para este CPF.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

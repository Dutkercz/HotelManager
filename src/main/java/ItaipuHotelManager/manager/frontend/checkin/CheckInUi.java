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
import java.util.Arrays;
import java.util.List;

public class CheckInUi {
    private final JDialog dialog;
    private final JTextField txtCpf;
    private final JButton btnConfirm;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private HotelClient selectedClient;
    private HotelRoom selectedRoom;
    private final JLabel lblClientName;
    private JSpinner spinnerNumDiarias;
    private JSpinner spinnerNumPessoas;
    private JTextArea txtPessoasAdicionais;

    public CheckInUi(JFrame parent) {
        dialog = new JDialog(parent, "Realizar Check-in", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(900, 550);
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

        tableModel = new DefaultTableModel(new Object[]{"Número", "Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);
        setupAdditionalFields();

        btnConfirm = new JButton("Confirmar Check-in");
        btnConfirm.setEnabled(false);
        dialog.add(btnConfirm, BorderLayout.SOUTH);

        btnSearch.addActionListener(this::buscarCliente);
        table.getSelectionModel().addListSelectionListener(e -> selectRoom());
        btnConfirm.addActionListener(e -> confirmarCheckIn());

        dialog.setVisible(true);
    }

    private void setupAdditionalFields() {
        JPanel extraPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        spinnerNumDiarias = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        spinnerNumPessoas = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        txtPessoasAdicionais = new JTextArea(3, 20);
        txtPessoasAdicionais.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        extraPanel.add(new JLabel("Número de Diárias:"));
        extraPanel.add(spinnerNumDiarias);
        extraPanel.add(new JLabel("Número de Pessoas Adicionais:"));
        extraPanel.add(spinnerNumPessoas);
        extraPanel.add(new JLabel("Nome dos hospedes adicionais (se houver):"));
        extraPanel.add(new JScrollPane(txtPessoasAdicionais));

        dialog.add(extraPanel, BorderLayout.EAST);
    }

    private void buscarCliente(ActionEvent e) {
        String cpf = txtCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Digite um CPF.", "Aviso!", JOptionPane.WARNING_MESSAGE);
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/clients/" + cpf;
        ResponseEntity<HotelClient> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<HotelClient>() {});

        selectedClient = response.getBody();
        tableModel.setRowCount(0);

        if (selectedClient == null) {
            JOptionPane.showMessageDialog(dialog, "Cliente não encontrado!", "Erro!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Hibernate.initialize(selectedClient);
        carregarApartamentosDisponiveis();
        lblClientName.setText("Nome do Cliente: " + selectedClient.getFullName());
    }

    private void carregarApartamentosDisponiveis() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/rooms/available";
        ResponseEntity<List<HotelRoom>> getAvailableRooms = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<HotelRoom>>() {});
        List<HotelRoom> availableRooms = getAvailableRooms.getBody();

        tableModel.setRowCount(0);
        if (availableRooms != null) {
            for (HotelRoom room : availableRooms) {
                tableModel.addRow(new Object[]{room.getRoomNumber(), room.getStatus()});
            }
        }
    }

    private void selectRoom() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String roomNumber = (String) tableModel.getValueAt(selectedRow, 0);

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/rooms/" + roomNumber;

            ResponseEntity<HotelRoom> getRoom = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<HotelRoom>() {});
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

            int numDiarias = (int) spinnerNumDiarias.getValue();
            int numPessoas = (int) spinnerNumPessoas.getValue();
            String nomesAdicionaisStr = txtPessoasAdicionais.getText().trim();

            List<HotelPerson> personsList = new ArrayList<>();

            if (!nomesAdicionaisStr.isEmpty()) {
                String[] nomesAdicionais = nomesAdicionaisStr.split("\n");
                for (String names : nomesAdicionais) {
                    personsList.add(new HotelPerson(names.trim()));
                }
            }
            if (personsList.size() != numPessoas) {
                JOptionPane.showMessageDialog(dialog,
                        "Número de pessoas não corresponde à quantidade informada!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDateTime checkIn = LocalDateTime.now();
            LocalDateTime checkOut = checkIn.plusDays(numDiarias);

            Hosting newHosting = new Hosting(null, numPessoas, numDiarias, selectedRoom, selectedClient,
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
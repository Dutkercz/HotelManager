package ItaipuHotelManager.manager.frontend.checkin;

import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.services.HostingService;
import ItaipuHotelManager.manager.services.HotelClientService;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.hibernate.Hibernate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CheckInUi {
    private JDialog dialog;
    private JTextField txtCpf;
    private JButton btnBuscar, btnConfirmar;
    private JTable table;
    private DefaultTableModel tableModel;
    private HotelClient selectedClient;
    private HotelRoom selectedRoom;
    private HotelRoomService roomService;
    private HotelClientService clientService;
    private HostingService hostingService;

    public CheckInUi(JFrame parent, HotelRoomService roomService, HotelClientService clientService, HostingService hostingService) {
        this.roomService = roomService;
        this.clientService = clientService;
        this.hostingService = hostingService;

        dialog = new JDialog(parent, "Realizar Check-in", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(700, 580);
        dialog.setLocationRelativeTo(parent);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("CPF do Cliente:"));
        txtCpf = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        topPanel.add(txtCpf);
        topPanel.add(btnBuscar);

        dialog.add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new Object[]{"Número", "Status"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        btnConfirmar = new JButton("Confirmar Check-in");
        btnConfirmar.setEnabled(false);
        dialog.add(btnConfirmar, BorderLayout.SOUTH);

        btnBuscar.addActionListener(this::buscarCliente);
        table.getSelectionModel().addListSelectionListener(e -> selecionarApartamento());
        btnConfirmar.addActionListener(e -> confirmarCheckIn());

        dialog.setVisible(true);
    }

    private void buscarCliente(ActionEvent e) {
        String cpf = txtCpf.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Digite um CPF.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        HotelClient client =  clientService.findByCpf(cpf);
        selectedClient.setFullName(client.getFullName());
        if (selectedClient == null) {
            JOptionPane.showMessageDialog(dialog, "Cliente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        carregarApartamentosDisponiveis();
    }

    private void carregarApartamentosDisponiveis() {
        tableModel.setRowCount(0);
        List<HotelRoom> availableApartments = roomService.getAvailableRooms();
        for (HotelRoom room : availableApartments) {
            tableModel.addRow(new Object[]{room.getRoomNumber(), room.getStatus()});
        }
    }

    private void selecionarApartamento() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int apartmentNumber = (int) tableModel.getValueAt(selectedRow, 0);
            selectedRoom = roomService.findByRoomNumber(String.valueOf((apartmentNumber)));
            btnConfirmar.setEnabled(true);
        }
    }

    private void confirmarCheckIn() {
        if (selectedClient != null && selectedRoom != null) {
            hostingService.checkIn(selectedClient, selectedRoom);
            JOptionPane.showMessageDialog(dialog, "Check-in realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        } else {
            JOptionPane.showMessageDialog(dialog, "Erro ao realizar check-in.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

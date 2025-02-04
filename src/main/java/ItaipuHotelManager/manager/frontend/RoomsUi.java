package ItaipuHotelManager.manager.frontend;

import ItaipuHotelManager.manager.entities.HotelRoom;
import ItaipuHotelManager.manager.services.HotelRoomService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class RoomsUi {
    private final JFrame frame;
    private final JPanel panel;
    private final JTable table;
    private final JButton btnCarregarApartamentos;

    public RoomsUi(HotelRoomService service) {
        frame = new JFrame("Apartamentos");
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        btnCarregarApartamentos = new JButton("Carregar Apartamentos");
        table = new JTable();

        DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Cliente", "Status"}, 0);
        table.setModel(model);

        btnCarregarApartamentos.addActionListener(e -> carregarApartamentos(model));

        panel.add(btnCarregarApartamentos, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void carregarApartamentos(DefaultTableModel model) {
        String url = "http://localhost:8080/rooms";
        RestTemplate restTemplate = new RestTemplate();

        try {
            ParameterizedTypeReference<Map<String, List<HotelRoom>>> responseType =
                    new ParameterizedTypeReference<Map<String, List<HotelRoom>>>() {
                    };

            ResponseEntity<Map<String, List<HotelRoom>>> responseEntity =
                    restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            Map<String, List<HotelRoom>> response = responseEntity.getBody();

            assert response != null;
            List<HotelRoom> apartamentosLivres = response.get("Livres");
            List<HotelRoom> apartamentosOcupados = response.get("Ocupados");

            model.setRowCount(0);


            for (HotelRoom room : apartamentosLivres) {
                model.addRow(new Object[]{room.getId(), "Livre", "Disponível"});
            }

            for (HotelRoom room : apartamentosOcupados) {
                String clienteNome = room.getClient() != null ? room.getClient().getFullName() : "Indisponível";
                model.addRow(new Object[]{room.getId(), clienteNome, "Ocupado"});
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar apartamentos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}

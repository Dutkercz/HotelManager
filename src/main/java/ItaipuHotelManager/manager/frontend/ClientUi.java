package ItaipuHotelManager.manager.frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import ItaipuHotelManager.manager.entities.HotelClient;
import ItaipuHotelManager.manager.entities.utils.CpfValidation;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.List;

public class ClientUi {
    private final JFrame frame;
    private final JPanel panel;
    private final JButton btnCarregar;
    private final JTable clienteTable;
    private final JButton btnCadastrar;

    public ClientUi() {
        frame = new JFrame("Clientes");
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        btnCarregar = new JButton("Carregar Clientes");
        btnCadastrar = new JButton("Cadastrar Cliente");

        String[] columnNames = {"ID", "Nome", "CPF", "Endereço"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        clienteTable = new JTable(model);

        btnCarregar.addActionListener(e -> carregarClientes(model));
        btnCadastrar.addActionListener(e -> abrirCadastro());

        panel.add(btnCarregar, BorderLayout.NORTH);
        panel.add(new JScrollPane(clienteTable), BorderLayout.CENTER);

        frame.add(panel);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void carregarClientes(DefaultTableModel model) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/clients";

        try {
            ResponseEntity<List<HotelClient>> response = restTemplate.exchange(url,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<HotelClient>>() {}
            );

            List<HotelClient> clientes = response.getBody();

            model.setRowCount(0);

            assert clientes != null;
            for (HotelClient cliente : clientes) {
                Object[] row = {cliente.getId(), cliente.getFullName(), cliente.getCpf(), cliente.getAddress()};
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Erro ao buscar clientes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCadastro() {
        JDialog cadastroDialog = new JDialog(frame, "Cadastro de Cliente", true);
        JPanel cadastroPanel = new JPanel();
        cadastroPanel.setLayout(new GridLayout(5, 2));

        JLabel lblFullName= new JLabel("Nome Completo:");
        JTextField txtFullName = new JTextField(20);

        JLabel lblCpf = new JLabel("CPF:");
        JTextField txtCpf = new JTextField(20);
        boolean cpfValidation = CpfValidation.isValidCPF(String.valueOf(txtCpf));
        while (!cpfValidation){
            lblCpf = new JLabel("CPF inválido, digite novamente:");
            txtCpf = new JTextField(20);
            cpfValidation = CpfValidation.isValidCPF(String.valueOf(txtCpf));
            if (cpfValidation){
                break;
            }
        }
        JTextField finalTxtCpf = txtCpf;


        JLabel lblAddress = new JLabel("Endereço com número:");
        JTextField txtAddress = new JTextField(20);

        JLabel lblCity = new JLabel("Cidade:");
        JTextField txtCity = new JTextField(20);

        JLabel lblPhone = new JLabel("Telefone:");
        JTextField txtPhone = new JTextField(20);

        JLabel lblEmail = new JLabel("email:");
        JTextField txtEmail = new JTextField(20);

        JLabel lblCNPJ = new JLabel("CNPJ:");
        JTextField txtCNPJ = new JTextField(20);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        cadastroPanel.add(lblFullName);
        cadastroPanel.add(txtFullName);
        cadastroPanel.add(lblCpf);
        cadastroPanel.add(txtCpf);
        cadastroPanel.add(lblAddress);
        cadastroPanel.add(txtAddress);
        cadastroPanel.add(lblCity);
        cadastroPanel.add(txtCity);
        cadastroPanel.add(lblPhone);
        cadastroPanel.add(txtPhone);
        cadastroPanel.add(lblEmail);
        cadastroPanel.add(txtEmail);
        cadastroPanel.add(lblCNPJ);
        cadastroPanel.add(txtCNPJ);


        cadastroPanel.add(btnSalvar);
        cadastroPanel.add(btnCancelar);

        cadastroDialog.add(cadastroPanel);
        cadastroDialog.pack();
        cadastroDialog.setLocationRelativeTo(frame);
        cadastroDialog.setVisible(true);

        // Ação do botão Salvar
        btnSalvar.addActionListener(e -> {
            String nome = txtFullName.getText();
            String cpf = finalTxtCpf.getText();
            String endereco = txtAddress.getText();
            String cidade = txtCity.getText();
            String telefone = txtCNPJ.getText();
            String email = txtEmail.getText();
            String cnpj = txtCNPJ.getText();

            if (nome.isEmpty() || cpf.isEmpty() || endereco.isEmpty()) {
                JOptionPane.showMessageDialog(cadastroDialog, "Os campos CPF, Nome e Endereço são obrigatórios",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                HotelClient novoCliente = new HotelClient(null, nome, cpf, cidade, endereco, email, telefone, cnpj);


                RestTemplate restTemplate = new RestTemplate();
                String url = "http://localhost:8080/clients";

                try {
                    HttpHeaders headers = new HttpHeaders();
                    HttpEntity<HotelClient> request = new HttpEntity<>(novoCliente, headers);
                    ResponseEntity<HotelClient> response = restTemplate.exchange(url, HttpMethod.POST, request, HotelClient.class);

                    if (response.getStatusCode() == HttpStatus.CREATED) {
                        JOptionPane.showMessageDialog(cadastroDialog, "Cliente cadastrado com sucesso!");
                        cadastroDialog.dispose(); // Fecha o formulário de cadastro
                        carregarClientes((DefaultTableModel) clienteTable.getModel()); // Atualiza a lista de clientes
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(cadastroDialog, "Erro ao cadastrar cliente: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Ação do botão Cancelar
        btnCancelar.addActionListener(e -> cadastroDialog.dispose());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClientUi::new);
    }
}
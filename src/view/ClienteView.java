package view;

import dao.ClienteDAO;
import model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteView extends JFrame {
    private ClienteDAO dao = new ClienteDAO();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtNome, txtCidade;

    public ClienteView() {
        setTitle("Clientes");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Cabeçalho ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        JLabel titleLabel = new JLabel("Clientes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Painel central ---
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(new Color(236, 240, 241));

        // Tabela
        String[] colunas = {"ID", "Nome", "Cidade"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // impede edição direta na tabela
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(41, 128, 185));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(149, 165, 166), 2));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // --- Painel inferior (form + botões) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Formulário
        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        txtId = new JTextField(5);
        txtNome = new JTextField(15);
        txtCidade = new JTextField(15);

        formPanel.add(new JLabel("ID:"));
        formPanel.add(txtId);
        formPanel.add(new JLabel("Nome:"));
        formPanel.add(txtNome);
        formPanel.add(new JLabel("Cidade:"));
        formPanel.add(txtCidade);

        bottomPanel.add(formPanel, BorderLayout.NORTH);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnInserir = createStyledButton("Inserir", new Color(46, 204, 113));
        JButton btnListar = createStyledButton("Listar", new Color(52, 152, 219));
        JButton btnAtualizar = createStyledButton("Atualizar", new Color(241, 196, 15));
        JButton btnDeletar = createStyledButton("Deletar", new Color(231, 76, 60));

        buttonPanel.add(btnInserir);
        buttonPanel.add(btnListar);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnDeletar);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        // --- Ações dos botões ---
        btnInserir.addActionListener(e -> {
            try {
                Cliente c = new Cliente(0, txtNome.getText(), txtCidade.getText());
                dao.inserir(c);
                listarClientes();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Cliente inserido!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao inserir: " + ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> listarClientes());

        btnAtualizar.addActionListener(e -> {
            try {
                Cliente c = new Cliente(
                        Integer.parseInt(txtId.getText()),
                        txtNome.getText(),
                        txtCidade.getText()
                );
                dao.atualizar(c);
                listarClientes();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Cliente atualizado!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar: " + ex.getMessage());
            }
        });

        btnDeletar.addActionListener(e -> {
            try {
                dao.deletar(Integer.parseInt(txtId.getText()));
                listarClientes();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Cliente deletado!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao deletar: " + ex.getMessage());
            }
        });

        // Carregar lista inicial
        listarClientes();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void listarClientes() {
        try {
            tableModel.setRowCount(0);
            List<Cliente> clientes = dao.listar();
            for (Cliente c : clientes) {
                tableModel.addRow(new Object[]{
                        c.getIdCliente(),
                        c.getNome(),
                        c.getCidade()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCidade.setText("");
    }
}

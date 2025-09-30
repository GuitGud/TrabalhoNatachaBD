package view;

import dao.ContratoDAO;
import model.Contrato;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.List;

public class ContratoView extends JFrame {
    private ContratoDAO dao = new ContratoDAO();
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtId, txtDataInicio, txtDataFim, txtValorMensal, txtIdCliente;

    public ContratoView() {
        setTitle("Contratos");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Cabeçalho ---
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80));
        JLabel titleLabel = new JLabel("Contratos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // --- Painel central (tabela) ---
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centerPanel.setBackground(new Color(236, 240, 241));

        String[] colunas = {"ID", "Data Início", "Data Fim", "Valor Mensal", "ID Cliente"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // --- Painel inferior (form + botões) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        txtId = new JTextField(5);
        txtDataInicio = new JTextField(10);
        txtDataFim = new JTextField(10);
        txtValorMensal = new JTextField(10);
        txtIdCliente = new JTextField(5);

        formPanel.add(new JLabel("ID:")); formPanel.add(txtId);
        formPanel.add(new JLabel("Data Início (yyyy-mm-dd):")); formPanel.add(txtDataInicio);
        formPanel.add(new JLabel("Data Fim (yyyy-mm-dd):")); formPanel.add(txtDataFim);
        formPanel.add(new JLabel("Valor Mensal:")); formPanel.add(txtValorMensal);
        formPanel.add(new JLabel("ID Cliente:")); formPanel.add(txtIdCliente);

        bottomPanel.add(formPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnInserir = createStyledButton("Inserir", new Color(46, 204, 113));
        JButton btnListar = createStyledButton("Listar", new Color(52, 152, 219));
        JButton btnAtualizar = createStyledButton("Atualizar", new Color(241, 196, 15));
        JButton btnDeletar = createStyledButton("Deletar", new Color(231, 76, 60));
        JButton btnJoin = createStyledButton("Listar com Cliente", new Color(155, 89, 182));

        buttonPanel.add(btnInserir);
        buttonPanel.add(btnListar);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnDeletar);
        buttonPanel.add(btnJoin);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Ações dos botões ---
        btnInserir.addActionListener(e -> {
            try {
                Contrato c = new Contrato(
                        0,
                        Date.valueOf(txtDataInicio.getText()),
                        Date.valueOf(txtDataFim.getText()),
                        Double.parseDouble(txtValorMensal.getText()),
                        Integer.parseInt(txtIdCliente.getText())
                );
                dao.inserir(c);
                listarContratos();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Contrato inserido!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnListar.addActionListener(e -> listarContratos());

        btnAtualizar.addActionListener(e -> {
            try {
                Contrato c = new Contrato(
                        Integer.parseInt(txtId.getText()),
                        Date.valueOf(txtDataInicio.getText()),
                        Date.valueOf(txtDataFim.getText()),
                        Double.parseDouble(txtValorMensal.getText()),
                        Integer.parseInt(txtIdCliente.getText())
                );
                dao.atualizar(c);
                listarContratos();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Contrato atualizado!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnDeletar.addActionListener(e -> {
            try {
                dao.deletar(Integer.parseInt(txtId.getText()));
                listarContratos();
                limparCampos();
                JOptionPane.showMessageDialog(this, "Contrato deletado!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnJoin.addActionListener(e -> {
            try {
                List<String> lista = dao.listarContratosComClientes();
                tableModel.setRowCount(0);
                for (String s : lista) {
                    tableModel.addRow(new Object[]{s});
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        listarContratos();
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
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

    private void listarContratos() {
        try {
            tableModel.setRowCount(0);
            List<Contrato> lista = dao.listar();
            for (Contrato c : lista) {
                tableModel.addRow(new Object[]{
                        c.getIdContrato(),
                        c.getDataInicio(),
                        c.getDataFim(),
                        c.getValorMensal(),
                        c.getIdCliente()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao listar contratos: " + ex.getMessage());
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtDataInicio.setText("");
        txtDataFim.setText("");
        txtValorMensal.setText("");
        txtIdCliente.setText("");
    }
}

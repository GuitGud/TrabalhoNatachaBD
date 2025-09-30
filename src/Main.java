import view.ClienteView;
import view.ContratoView;
import view.GraficoMediaView;
import dao.ContratoEstatisticaDAO;
import dao.ConnectionFactory;

import javax.swing.*;
import java.sql.Connection;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame menu = new JFrame("Menu Principal");
            menu.setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Painel com layout vertical
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            // Espaçamento entre botões
            int espacamento = 20;

            // Botão Clientes
            JButton btnCliente = new JButton("Gerenciar Clientes");
            btnCliente.addActionListener(e -> new ClienteView().setVisible(true));
            btnCliente.setAlignmentX(JButton.CENTER_ALIGNMENT);
            panel.add(btnCliente);
            panel.add(Box.createVerticalStrut(espacamento));

            // Botão Contratos
            JButton btnContrato = new JButton("Gerenciar Contratos");
            btnContrato.addActionListener(e -> new ContratoView().setVisible(true));
            btnContrato.setAlignmentX(JButton.CENTER_ALIGNMENT);
            panel.add(btnContrato);
            panel.add(Box.createVerticalStrut(espacamento));

            // Botão Gráfico Média
            JButton btnGrafico = new JButton("Mostrar Gráfico da Média dos Contratos");
            btnGrafico.addActionListener(e -> {
                try (Connection conn = ConnectionFactory.getConnection()) {
                    ContratoEstatisticaDAO dao = new ContratoEstatisticaDAO(conn);

                    double mediaGeral = dao.calcularMediaGeral();
                    Map<Integer, Double> medias = dao.calcularMediaPorCliente();

                    new GraficoMediaView(medias, mediaGeral).setVisible(true);

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao gerar gráfico: " + ex.getMessage());
                }
            });
            btnGrafico.setAlignmentX(JButton.CENTER_ALIGNMENT);
            panel.add(btnGrafico);

            // Centralizar no frame
            menu.add(panel);
            menu.setVisible(true);
        });
    }
}

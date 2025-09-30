import view.ClienteView;
import view.ContratoView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame menu = new JFrame("Menu Principal");
            menu.setSize(2080, 1800);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel panel = new JPanel();

            JButton btnCliente = new JButton("Gerenciar Clientes");
            btnCliente.addActionListener(e -> new ClienteView().setVisible(true));

            JButton btnContrato = new JButton("Gerenciar Contratos");
            btnContrato.addActionListener(e -> new ContratoView().setVisible(true));

            panel.add(btnCliente);
            panel.add(btnContrato);
            menu.add(panel);
            menu.setVisible(true);
        });
    }
}

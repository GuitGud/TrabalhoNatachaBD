package view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class GraficoMediaView extends JFrame {
    private Map<Integer, Double> medias;
    private double mediaGeral;

    public GraficoMediaView(Map<Integer, Double> medias, double mediaGeral) {
        this.medias = medias;
        this.mediaGeral = mediaGeral;

        setTitle("Média dos Contratos (Geral e por Cliente)");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        add(new PainelGrafico());
    }

    private class PainelGrafico extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            int larguraBarra = 30;
            int espacamento = 15;
            int x = 50;
            int alturaMax = getHeight() - 60;

            // Descobre o maior valor (considerando médias por cliente + média geral)
            double maiorMedia = mediaGeral;
            for (double media : medias.values()) {
                if (media > maiorMedia) {
                    maiorMedia = media;
                }
            }

            // --- Desenha barra da média geral ---
            int alturaBarraGeral = (int) ((mediaGeral / maiorMedia) * alturaMax);
            g2.setColor(Color.RED);
            g2.fillRect(x, alturaMax - alturaBarraGeral, larguraBarra, alturaBarraGeral);
            g2.setColor(Color.BLACK);
            g2.drawString("Geral", x, alturaMax + 15);
            g2.drawString(String.format("%.2f", mediaGeral), x, alturaMax - alturaBarraGeral - 5);

            // --- Desenha barras por cliente ---
            x += larguraBarra + espacamento;
            for (Map.Entry<Integer, Double> entry : medias.entrySet()) {
                int idCliente = entry.getKey();
                double media = entry.getValue();

                int alturaBarra = (int) ((media / maiorMedia) * alturaMax);

                g2.setColor(Color.BLUE);
                g2.fillRect(x, alturaMax - alturaBarra, larguraBarra, alturaBarra);

                g2.setColor(Color.BLACK);
                g2.drawString("C" + idCliente, x, alturaMax + 15);
                g2.drawString(String.format("%.2f", media), x, alturaMax - alturaBarra - 5);

                x += larguraBarra + espacamento;
            }
        }
    }
}

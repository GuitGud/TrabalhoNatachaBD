package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ContratoEstatisticaDAO {
    private Connection connection;

    public ContratoEstatisticaDAO(Connection connection) {
        this.connection = connection;
    }

    // Média geral
    public double calcularMediaGeral() throws Exception {
        String sql = "SELECT valor_mensal FROM contrato";
        double soma = 0;
        int count = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                soma += rs.getDouble("valor_mensal");
                count++;
            }
        }
        return (count > 0) ? soma / count : 0.0;
    }

    // Média por cliente
    public Map<Integer, Double> calcularMediaPorCliente() throws Exception {
        String sql = "SELECT id_cliente, AVG(valor_mensal) AS media FROM contrato GROUP BY id_cliente";
        Map<Integer, Double> medias = new HashMap<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                double media = rs.getDouble("media");
                medias.put(idCliente, media);
            }
        }
        return medias;
    }
}

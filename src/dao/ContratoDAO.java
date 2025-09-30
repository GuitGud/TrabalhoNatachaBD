package dao;

import model.Contrato;
import java.sql.*;
import java.util.*;

public class ContratoDAO {

    public void inserir(Contrato c) throws Exception {
        String sql = "INSERT INTO Contrato (data_inicio, data_fim, valor_mensal, id_cliente) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, c.getDataInicio());
            ps.setDate(2, c.getDataFim());
            ps.setDouble(3, c.getValorMensal());
            ps.setInt(4, c.getIdCliente());
            ps.executeUpdate();
        }
    }

    public List<Contrato> listar() throws Exception {
        List<Contrato> lista = new ArrayList<>();
        String sql = "SELECT * FROM Contrato";
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Contrato c = new Contrato(
                        rs.getInt("id_contrato"),
                        rs.getDate("data_inicio"),
                        rs.getDate("data_fim"),
                        rs.getDouble("valor_mensal"),
                        rs.getInt("id_cliente")
                );
                lista.add(c);
            }
        }
        return lista;
    }

    public void atualizar(Contrato c) throws Exception {
        String sql = "UPDATE Contrato SET data_inicio=?, data_fim=?, valor_mensal=?, id_cliente=? WHERE id_contrato=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, c.getDataInicio());
            ps.setDate(2, c.getDataFim());
            ps.setDouble(3, c.getValorMensal());
            ps.setInt(4, c.getIdCliente());
            ps.setInt(5, c.getIdContrato());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Contrato WHERE id_contrato=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // JOIN: listar contratos com nome do cliente
    public List<String> listarContratosComClientes() throws Exception {
        List<String> lista = new ArrayList<>();
        String sql = """
            SELECT c.id_contrato, c.valor_mensal, cl.nome
            FROM Contrato c
            JOIN Cliente cl ON c.id_cliente = cl.id_cliente
        """;
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                String linha = "Contrato " + rs.getInt("id_contrato") +
                               " - Cliente: " + rs.getString("nome") +
                               " - Valor: " + rs.getDouble("valor_mensal");
                lista.add(linha);
            }
        }
        return lista;
    }
}

package dao;
import model.Cliente;
import java.sql.*;
import java.util.*;

public class ClienteDAO {

    public void inserir(Cliente c) throws Exception {
        String sql = "INSERT INTO Cliente (nome, cidade) VALUES (?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCidade());
            ps.executeUpdate();
        }
    }

    public List<Cliente> listar() throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection con = ConnectionFactory.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente(rs.getInt("id_cliente"),
                                        rs.getString("nome"),
                                        rs.getString("cidade"));
                lista.add(c);
            }
        }
        return lista;
    }

    public void atualizar(Cliente c) throws Exception {
        String sql = "UPDATE Cliente SET nome=?, cidade=? WHERE id_cliente=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getCidade());
            ps.setInt(3, c.getIdCliente());
            ps.executeUpdate();
        }
    }

    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM Cliente WHERE id_cliente=?";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}

package br.com.fiap.eco.dao;

import br.com.fiap.eco.connection.ConexaoBanco;
import br.com.fiap.eco.model.Depoimento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepoimentoDao {

    public List<Depoimento> buscarTodos() throws SQLException {
        String sql = "SELECT d.ID_DEPOIMENTO, d.ID_CLIENTE, d.DESCRICAO, c.NOME AS NOME_CLIENTE " +
                "FROM T_ECO_DEPOIMENTOS d " +
                "JOIN T_ECO_CLIENTE c ON d.ID_CLIENTE = c.ID_CLIENTE";

        List<Depoimento> depoimentos = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Depoimento depoimento = new Depoimento();
                depoimento.setIdDepoimento(rs.getInt("ID_DEPOIMENTO"));
                depoimento.setIdCliente(rs.getInt("ID_CLIENTE"));
                depoimento.setDescricao(rs.getString("DESCRICAO"));
                depoimento.setNomeCliente(rs.getString("NOME_CLIENTE"));

                depoimentos.add(depoimento);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar depoimentos: " + e.getMessage(), e);
        }

        return depoimentos; // Retorna a lista de depoimentos
    }
    public void inserirDepoimento(Depoimento depoimento) throws SQLException {
        String sql = "INSERT INTO T_ECO_DEPOIMENTOS (ID_CLIENTE, DESCRICAO) VALUES (?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, depoimento.getIdCliente());
            stmt.setString(2, depoimento.getDescricao());

            stmt.executeUpdate();
        }
    }
    public boolean existeCliente(int idCliente) throws SQLException {
        String sql = "SELECT 1 FROM t_eco_cliente WHERE id_cliente = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao verificar a existência do cliente: " + e.getMessage(), e);
        }
    }
}

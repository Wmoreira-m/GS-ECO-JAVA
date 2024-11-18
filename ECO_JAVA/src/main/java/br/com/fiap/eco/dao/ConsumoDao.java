package br.com.fiap.eco.dao;

import br.com.fiap.eco.connection.ConexaoBanco;
import br.com.fiap.eco.model.Consumo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ConsumoDao {

    public void inserirConsumo(Consumo consumo) throws SQLException {
        String sql = "INSERT INTO T_ECO_CONSUMO_CLIENTE (ID_CLIENTE, CUSTO_MENSAL, KW_MES, DISTRIBUIDORA, TARIFA, DATA_CONSUMO, ID_ENDERECO) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, consumo.getIdCliente());
            stmt.setDouble(2, consumo.getCustoMensal());
            stmt.setDouble(3, consumo.getKwMes());
            stmt.setString(4, consumo.getDistribuidora());
            stmt.setDouble(5, consumo.getTarifa());
            stmt.setString(6, consumo.getDataConsumo());
            stmt.setInt(7, consumo.getIdEndereco());

            stmt.executeUpdate();
        }
    }

    public boolean clienteExiste(int idCliente) throws SQLException {
        String sql = "SELECT 1 FROM T_ECO_CLIENTE WHERE ID_CLIENTE = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Retorna true se o cliente existir
            }
        }
    }

    public boolean enderecoExiste(int idEndereco) throws SQLException {
        String sql = "SELECT 1 FROM T_ECO_ENDERECO WHERE ID_ENDERECO = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEndereco);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }



    public List<Consumo> buscarConsumosPorClienteEEndereco(int idCliente, int idEndereco) throws SQLException {
        String sql = "SELECT * FROM T_ECO_CONSUMO_CLIENTE WHERE ID_CLIENTE = ? AND ID_ENDERECO = ?";
        List<Consumo> consumos = new ArrayList<>();


        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente );
            stmt.setInt(2, idEndereco );

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Consumo consumo = new Consumo();
                    consumo.setIdConsumoCli(rs.getInt("ID_CONSUMO_CLI"));
                    consumo.setIdCliente(rs.getInt("ID_CLIENTE"));
                    consumo.setCustoMensal(rs.getDouble("CUSTO_MENSAL"));
                    consumo.setKwMes(rs.getDouble("KW_MES"));
                    consumo.setDistribuidora(rs.getString("DISTRIBUIDORA"));
                    consumo.setTarifa(rs.getDouble("TARIFA"));
                    consumo.setDataConsumo(rs.getString("DATA_CONSUMO"));
                    consumos.add(consumo);
                }
            }
        }
        return consumos;
    }

    public boolean consumoExiste(int idCliente, int idEndereco) throws SQLException {
        String sql = "SELECT 1 FROM T_ECO_CONSUMO_CLIENTE WHERE ID_CLIENTE = ? AND ID_ENDERECO = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.setInt(2, idEndereco);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se existe consumo
            }
        }
    }


    public void atualizarConsumo(int idCliente, int idEndereco, Consumo consumoAtualizado) throws SQLException {
        String sql = "UPDATE T_ECO_CONSUMO_CLIENTE SET " +
                "CUSTO_MENSAL = ?, " +
                "KW_MES = ?, " +
                "DISTRIBUIDORA = ?, " +
                "TARIFA = ?, " +
                "DATA_CONSUMO = ? " +
                "WHERE ID_CLIENTE = ? AND ID_ENDERECO = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Setando os parâmetros para a query
            stmt.setDouble(1, consumoAtualizado.getCustoMensal());
            stmt.setDouble(2, consumoAtualizado.getKwMes());
            stmt.setString(3, consumoAtualizado.getDistribuidora());
            stmt.setDouble(4, consumoAtualizado.getTarifa());
            stmt.setString(5, consumoAtualizado.getDataConsumo());
            stmt.setInt(6, idCliente);
            stmt.setInt(7, idEndereco);

            // Executando a atualização
            int rowsUpdated = stmt.executeUpdate();

            // Se nenhuma linha foi atualizada, lançar uma exceção
            if (rowsUpdated == 0) {
                throw new SQLException("Erro ao atualizar o consumo: Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            // Lança uma exceção com uma mensagem detalhada
            throw new SQLException("Erro ao atualizar o consumo.", e);
        }
    }

    public void removerConsumo(int idCliente, int idEndereco) throws SQLException {
        String sql = "DELETE FROM T_ECO_CONSUMO_CLIENTE WHERE ID_CLIENTE = ? AND ID_ENDERECO = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idEndereco);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Erro ao remover o consumo: Nenhuma linha foi afetada.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover o consumo.", e);
        }
    }
}
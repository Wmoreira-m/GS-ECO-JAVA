package br.com.fiap.eco.dao;

import br.com.fiap.eco.connection.ConexaoBanco;
import br.com.fiap.eco.model.ConsumoSimulacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsumoSimulacaoDao {

    public void criarSimulacao(ConsumoSimulacao simulacao, String dataConsumo) throws SQLException {
        String sql = "INSERT INTO T_ECO_CONSUMO_SIMULACAO (ID_CLIENTE, CUSTO_FINAL, QTD_PLACAS, MODELO_PLACA, " +
                "KW_TOTAL_GERADO, ECONOMIA_GERADA, TOTAL_INVESTIR, ID_CONSUMO_CLI, ID_ENDERECO, GRID, DT_SIMULACAO) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        dataConsumo = buscarDataConsumo(simulacao.getIdConsumoCli());

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, simulacao.getIdCliente());
            stmt.setDouble(2, simulacao.getCustoFinal());
            stmt.setInt(3, simulacao.getQtdPlacas());
            stmt.setString(4, simulacao.getModeloPlaca());
            stmt.setDouble(5, simulacao.getKwTotalGerado());
            stmt.setDouble(6, simulacao.getEconomiaGerada());
            stmt.setDouble(7, simulacao.getTotalInvestir());
            stmt.setInt(8, simulacao.getIdConsumoCli());
            stmt.setInt(9, simulacao.getIdEndereco());
            stmt.setString(10, simulacao.getGrid());
            stmt.setString(11, dataConsumo);
            stmt.executeUpdate();
        }
    }

    public boolean simulacaoExiste(int idCliente, int idEndereco, int idConsumoSimu) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM T_ECO_CONSUMO_SIMULACAO " +
                "WHERE ID_CLIENTE = ? AND ID_ENDERECO = ? AND ID_CONSUMO_SUMI = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idEndereco);
            stmt.setInt(3, idConsumoSimu);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total") > 0;
                }
            }
        }
        return false;
    }


    public void removerSimulacaoEspecifica(int idCliente, int idEndereco, int idConsumoSimu) throws SQLException {
        String sql = "DELETE FROM T_ECO_CONSUMO_SIMULACAO WHERE ID_CLIENTE = ? AND ID_ENDERECO = ? AND ID_CONSUMO_SUMI = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idEndereco);
            stmt.setInt(3, idConsumoSimu);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("Erro ao deletar simulação: Nenhuma linha foi afetada.");
            }
        }
    }


    public boolean consumoExiste(int idConsumoCli) throws SQLException {
        String sql = "SELECT 1 FROM T_ECO_CONSUMO_CLIENTE WHERE ID_CONSUMO_CLI = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConsumoCli);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Obter a data do consumo
    public String buscarDataConsumo(int idConsumoCli) throws SQLException {
        String sql = "SELECT DATA_CONSUMO FROM T_ECO_CONSUMO_CLIENTE WHERE ID_CONSUMO_CLI = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConsumoCli);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("DATA_CONSUMO");
                } else {
                    throw new SQLException("Consumo não encontrado para o ID fornecido.");
                }
            }
        }
    }
    public void atualizarSimulacao(ConsumoSimulacao simulacao, String dataConsumo) throws SQLException {
        String sql = "UPDATE T_ECO_CONSUMO_SIMULACAO SET CUSTO_FINAL = ?, QTD_PLACAS = ?, MODELO_PLACA = ?, " +
                "KW_TOTAL_GERADO = ?, ECONOMIA_GERADA = ?, TOTAL_INVESTIR = ?, GRID = ?, DT_SIMULACAO = ? " +
                "WHERE ID_CONSUMO_SUMI = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, simulacao.getCustoFinal());
            stmt.setInt(2, simulacao.getQtdPlacas());
            stmt.setString(3, simulacao.getModeloPlaca());
            stmt.setDouble(4, simulacao.getKwTotalGerado());
            stmt.setDouble(5, simulacao.getEconomiaGerada());
            stmt.setDouble(6, simulacao.getTotalInvestir());
            stmt.setString(7, simulacao.getGrid());
            stmt.setString(8, dataConsumo);
            stmt.setInt(9, simulacao.getIdConsumoSimu() );

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Erro ao atualizar simulação: Nenhuma linha foi afetada.");
            }
        }
    }

    public ConsumoSimulacao buscarSimulacaoPorId(int idConsumoSimu) throws SQLException {
        String sql = "SELECT * FROM T_ECO_CONSUMO_SIMULACAO WHERE ID_CONSUMO_SUMI = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idConsumoSimu);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ConsumoSimulacao(
                            rs.getInt("ID_CONSUMO_SUMI"),
                            rs.getInt("ID_CLIENTE"),
                            rs.getDouble("CUSTO_FINAL"),
                            rs.getInt("QTD_PLACAS"),
                            rs.getString("MODELO_PLACA"),
                            rs.getDouble("KW_TOTAL_GERADO"),
                            rs.getDouble("ECONOMIA_GERADA"),
                            rs.getDouble("TOTAL_INVESTIR"),
                            rs.getInt("ID_CONSUMO_CLI"),
                            rs.getInt("ID_ENDERECO"),
                            rs.getString("GRID"),
                            rs.getString("DT_SIMULACAO")
                    );
                }
            }
        }
        return null;
    }

    public boolean clienteExiste(int idCliente) throws SQLException {
        String sql = "SELECT 1 FROM T_ECO_CLIENTE WHERE ID_CLIENTE = ?";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
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

    public List<ConsumoSimulacao> buscarSimulacoes(int idCliente, int idEndereco) throws SQLException {
        String sql = "SELECT * FROM T_ECO_CONSUMO_SIMULACAO WHERE ID_CLIENTE = ? AND ID_ENDERECO = ?";
        List<ConsumoSimulacao> simulacoes = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idEndereco);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ConsumoSimulacao simulacao = new ConsumoSimulacao(
                            rs.getInt("ID_CONSUMO_SUMI"),
                            rs.getInt("ID_CLIENTE"),
                            rs.getDouble("CUSTO_FINAL"),
                            rs.getInt("QTD_PLACAS"),
                            rs.getString("MODELO_PLACA"),
                            rs.getDouble("KW_TOTAL_GERADO"),
                            rs.getDouble("ECONOMIA_GERADA"),
                            rs.getDouble("TOTAL_INVESTIR"),
                            rs.getInt("ID_CONSUMO_CLI"),
                            rs.getInt("ID_ENDERECO"),
                            rs.getString("GRID"),
                            rs.getString("DT_SIMULACAO")
                    );
                    simulacoes.add(simulacao);
                }
            }
        }
        return simulacoes;
    }



    public void removerSimulacao(int idCliente, int idEndereco) throws SQLException {
        String sql = "DELETE FROM T_ECO_CONSUMO_SIMULACAO WHERE ID_CLIENTE = ? AND ID_ENDERECO = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            stmt.setInt(2, idEndereco);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("Erro ao deletar simulação: Nenhuma linha foi afetada.");
            }
        }
    }
}
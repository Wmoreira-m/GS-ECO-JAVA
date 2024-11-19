package br.com.fiap.eco.dao;

import br.com.fiap.eco.connection.ConexaoBanco;
import br.com.fiap.eco.model.Endereco;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnderecoDao {

    public void inserirEndereco(Endereco endereco) throws SQLException {
        String sql = "INSERT INTO T_ECO_ENDERECO (CEP, APELIDO_LOCAL, LOCALIDADE, UF, REGIAO, INSOLACAO_MD, ID_CLIENTE) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getApelidoLocal());
            stmt.setString(3, endereco.getLocalidade());
            stmt.setString(4, endereco.getUf());
            stmt.setString(5, endereco.getRegiao());
            stmt.setString(6, endereco.getInsolacaoMd());
            stmt.setInt(7, endereco.getIdCliente());
            stmt.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            throw new SQLException("Erro de integridade ao inserir endereço: Verifique se o cliente existe.", e);
        } catch (SQLException e) {
            throw new SQLException("Erro ao inserir endereço no banco de dados.", e);
        }
    }

    public void atualizarEndereco(Endereco  endereco ) throws SQLException {
        String sql = "UPDATE T_ECO_ENDERECO SET CEP = ?, APELIDO_LOCAL = ?, LOCALIDADE = ?, UF = ?, REGIAO = ?, INSOLACAO_MD = ?, ID_CLIENTE = ? WHERE ID_ENDERECO = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, endereco.getCep());
            stmt.setString(2, endereco.getApelidoLocal());
            stmt.setString(3, endereco.getLocalidade());
            stmt.setString(4, endereco.getUf());
            stmt.setString(5, endereco.getRegiao());
            stmt.setString(6, endereco.getInsolacaoMd());
            stmt.setInt(7, endereco.getIdCliente());
            stmt.setInt(8, endereco.getIdEndereco());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Endereço não encontrado.");
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar problema.", e);
        }
    }


    public List<Endereco> listar() throws SQLException {
        List<Endereco> enderecos = new ArrayList<>();
        String sql = "SELECT * FROM T_ECO_ENDERECO";

        try (Connection connection = ConexaoBanco.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Endereco endereco = new Endereco(
                        rs.getInt("ID_ENDERECO"),
                        rs.getString("CEP"),
                        rs.getString("APELIDO_LOCAL"),
                        rs.getString("LOCALIDADE"),
                        rs.getString("UF"),
                        rs.getString("REGIAO"),
                        rs.getString("INSOLACAO_MD"),
                        rs.getInt("ID_CLIENTE")
                );
                enderecos.add(endereco);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao listar enderecos.", e);
        }
        return enderecos;
    }

    public List<Endereco> buscarEnderecosPorCliente(int idCliente) throws SQLException {
        String sql = "SELECT * FROM T_ECO_ENDERECO WHERE ID_CLIENTE = ?";
        List<Endereco> enderecos = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Endereco endereco = new Endereco(
                            rs.getInt("ID_ENDERECO"),
                            rs.getString("CEP"),
                            rs.getString("APELIDO_LOCAL"),
                            rs.getString("LOCALIDADE"),
                            rs.getString("UF"),
                            rs.getString("REGIAO"),
                            rs.getString("INSOLACAO_MD"),
                            rs.getInt("ID_CLIENTE")
                    );
                    enderecos.add(endereco);
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar endereços.", e);
        }
        return enderecos;
    }


    public boolean verificarClienteExistente(int idCliente) throws SQLException {
        String sql = "SELECT 1 FROM T_ECO_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao verificar a existência do cliente.", e);
        }
    }


    public Endereco buscarEnderecoPorId(int idEndereco) throws SQLException {
        String sql = "SELECT * FROM T_ECO_ENDERECO WHERE ID_ENDERECO = ?";
        Endereco endereco = null;

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEndereco);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    endereco = new Endereco(
                            rs.getInt("ID_ENDERECO"),
                            rs.getString("CEP"),
                            rs.getString("APELIDO_LOCAL"),
                            rs.getString("LOCALIDADE"),
                            rs.getString("UF"),
                            rs.getString("REGIAO"),
                            rs.getString("INSOLACAO_MD"),
                            rs.getInt("ID_CLIENTE")
                    );
                }
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar endereço.", e);
        }

        return endereco;
    }


    public void removerEndereco(int id) throws SQLException {
        String sql = "DELETE FROM T_ECO_ENDERECO WHERE ID_ENDERECO = ?";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover endereço.", e);
        }
    }
}
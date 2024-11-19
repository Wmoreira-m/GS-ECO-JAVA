package br.com.fiap.eco.dao;

import br.com.fiap.eco.connection.ConexaoBanco;
import br.com.fiap.eco.model.Cliente;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {

    public void inserirCliente(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO T_ECO_CLIENTE (NOME, EMAIL, SENHA) VALUES (?, ?, ?)";

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.executeUpdate();

        } catch (SQLException e) {
            String errorMessage = "Erro ao inserir cliente: ";
            if (e.getErrorCode() == 1) {
                errorMessage += "Já existe um cliente com esse email";
            } else {
                errorMessage += e.getMessage();
            }
            throw new SQLException(errorMessage, e);
        }
    }

    public Cliente buscarClientePorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM T_ECO_CLIENTE WHERE EMAIL = ?";
        Cliente cliente = null;

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("ID_CLIENTE"),
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getString("DT_ATIVACAO")
                );
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar cliente.", e);
        }
        return cliente;
    }

    public Cliente buscarClientePorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_ECO_CLIENTE WHERE ID_CLIENTE = ?";
        Cliente cliente = null;

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("ID_CLIENTE"),
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getString("DT_ATIVACAO")
                );
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar cliente.", e);
        }
        return cliente;
    }



    public Cliente buscarClientePorLogin(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM T_ECO_CLIENTE WHERE EMAIL = ?";
        Cliente cliente = null;

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                if (rs.getString("SENHA").equals(senha))
                {
                    cliente = new Cliente(
                            rs.getInt("ID_CLIENTE"),
                            rs.getString("NOME"),
                            rs.getString("EMAIL"),
                            rs.getString("SENHA"),
                            rs.getString("DT_ATIVACAO")
                    );
                } else {
                    throw new SQLException("Senha incorreta");
                }
            } else {
                throw new SQLException("email não encontrado");
            }

        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        }
        return cliente;
    }



    public List<Cliente> listar() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM T_ECO_CLIENTE";

        try (Connection connection = ConexaoBanco.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("ID_CLIENTE"),
                        rs.getString("NOME"),
                        rs.getString("EMAIL"),
                        rs.getString("SENHA"),
                        rs.getString("DT_ATIVACAO")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao listar clientes.", e);
        }
        return clientes;
    }

    public void atualizarCliente(Cliente cliente) throws SQLException {
        String sql = "UPDATE T_ECO_CLIENTE SET NOME = ?, EMAIL = ?, SENHA = ?, DT_ATIVACAO = ? WHERE ID_CLIENTE = ?";

        if (cliente.getDtAtivacao() == null) {
            String consultaSql = "SELECT DT_ATIVACAO FROM T_ECO_CLIENTE WHERE ID_CLIENTE = ?";
            try (Connection conn = ConexaoBanco.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(consultaSql)) {

                stmt.setInt(1, cliente.getIdCliente());
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cliente.setDtAtivacao(dateFormat.format(rs.getTimestamp("DT_ATIVACAO")));
                    } else {
                        throw new SQLException("Cliente não encontrado com o ID fornecido.");
                    }
                }
            }
        }

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getSenha());
            stmt.setTimestamp(4, Timestamp.valueOf(cliente.getDtAtivacao()));
            stmt.setInt(5, cliente.getIdCliente());

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated == 0) {
                throw new SQLException("Cliente não encontrado.");
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar cliente.", e);
        }
    }


    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM T_ECO_CLIENTE WHERE ID_CLIENTE = ?";

        try (Connection connection = ConexaoBanco.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted == 0) {
                throw new SQLException("Cliente não encontrado para remoção.");
            }

        } catch (SQLException e) {
            throw new SQLException("Erro ao remover cliente.", e);
        }
    }
}

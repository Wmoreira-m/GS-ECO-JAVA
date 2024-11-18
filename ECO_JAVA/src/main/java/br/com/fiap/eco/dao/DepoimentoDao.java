package br.com.fiap.eco.dao;

import br.com.fiap.eco.connection.ConexaoBanco;
import br.com.fiap.eco.model.Depoimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepoimentoDao {

    public List<Depoimento> buscarTodos() throws SQLException {
        String sql = "SELECT ID_DEPOIMENTO, ID_CLIENTE, DESCRICAO, DT_DEPOIMENTO FROM T_ECO_DEPOIMENTOS";
        List<Depoimento> depoimentos = new ArrayList<>();

        try (Connection conn = ConexaoBanco.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Depoimento depoimento = new Depoimento();
                depoimento.setIdDepoimento(rs.getInt("ID_DEPOIMENTO"));
                depoimento.setIdCliente(rs.getInt("ID_CLIENTE"));
                depoimento.setDescricao(rs.getString("DESCRICAO"));
                depoimento.setDataDepoimento(rs.getString("DT_DEPOIMENTO"));

                depoimentos.add(depoimento);
            }
        }
        return depoimentos;
    }
}


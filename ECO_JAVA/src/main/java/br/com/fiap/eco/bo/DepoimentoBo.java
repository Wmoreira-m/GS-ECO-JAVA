package br.com.fiap.eco.bo;

import br.com.fiap.eco.dao.DepoimentoDao;
import br.com.fiap.eco.model.Depoimento;
import java.sql.SQLException;
import java.util.List;

public class DepoimentoBo {

    private DepoimentoDao depoimentoDAO;

    public DepoimentoBo() {
        this.depoimentoDAO = new DepoimentoDao();
    }

    public List<Depoimento> buscarTodos() throws SQLException {
        return depoimentoDAO.buscarTodos();
    }

    public void criarDepoimento(Depoimento depoimento) throws SQLException {
        validarDepoimento(depoimento);
        depoimentoDAO.inserirDepoimento(depoimento);
    }

    public boolean existeCliente(int idCliente) throws SQLException {
        return depoimentoDAO.existeCliente(idCliente);
    }

    private void validarDepoimento(Depoimento depoimento) {
        if (depoimento.getDescricao() == null || depoimento.getDescricao().isEmpty()) {
            throw new IllegalArgumentException("A descrição do depoimento é obrigatória.");
        }
    }
}

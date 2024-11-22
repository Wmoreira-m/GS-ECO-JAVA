package br.com.fiap.eco.bo;

import br.com.fiap.eco.dao.ConsumoSimulacaoDao;
import br.com.fiap.eco.model.ConsumoSimulacao;
import java.sql.SQLException;
import java.util.List;

public class ConsumoSimulacaoBo {

    private ConsumoSimulacaoDao simulacaoDAO;

    public ConsumoSimulacaoBo() {
        this.simulacaoDAO = new ConsumoSimulacaoDao();
    }

    public boolean clienteExiste(int idCliente) throws SQLException {
        return simulacaoDAO.clienteExiste(idCliente);
    }

    public boolean enderecoExiste(int idEndereco) throws SQLException {
        return simulacaoDAO.enderecoExiste(idEndereco);
    }

    public boolean consumoExiste(int idConsumoCli) throws SQLException {
        return simulacaoDAO.consumoExiste(idConsumoCli);
    }

    public void criarSimulacao(ConsumoSimulacao simulacao) throws SQLException {
        String dataConsumo = simulacaoDAO.buscarDataConsumo(simulacao.getIdConsumoCli());
        simulacaoDAO.criarSimulacao(simulacao, dataConsumo);
    }

    public ConsumoSimulacao buscarSimulacaoPorId(int idConsumoSimu) throws SQLException {
        return simulacaoDAO.buscarSimulacaoPorId(idConsumoSimu);
    }

    public void atualizarSimulacao(ConsumoSimulacao simulacaoAtualizada, ConsumoSimulacao simulacaoExistente) throws SQLException {
        String dataConsumo = simulacaoDAO.buscarDataConsumo(simulacaoExistente.getIdConsumoCli());

        if (simulacaoAtualizada.getGrid() != null) {
            simulacaoExistente.setGrid(simulacaoAtualizada.getGrid());
        }

        simulacaoDAO.atualizarSimulacao(simulacaoExistente, dataConsumo);
    }

    public List<ConsumoSimulacao> buscarSimulacoes(int idCliente, int idEndereco) throws SQLException {
        return simulacaoDAO.buscarSimulacoes(idCliente, idEndereco);
    }

    public void removerSimulacaoEspecifica(int idCliente, int idEndereco, int idConsumoSimu) throws SQLException {
        simulacaoDAO.removerSimulacaoEspecifica(idCliente, idEndereco, idConsumoSimu);
    }

    public boolean simulacaoExiste(int idCliente, int idEndereco, int idConsumoSimu) throws SQLException {
        return simulacaoDAO.simulacaoExiste(idCliente, idEndereco, idConsumoSimu);
    }
}

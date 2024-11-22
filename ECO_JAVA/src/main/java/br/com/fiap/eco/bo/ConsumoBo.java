package br.com.fiap.eco.bo;

import br.com.fiap.eco.dao.ConsumoDao;
import br.com.fiap.eco.model.Consumo;
import java.sql.SQLException;
import java.util.List;

public class ConsumoBo {

    private final ConsumoDao consumoDao = new ConsumoDao();

    public void criarConsumo(Consumo consumo) throws SQLException {
        if (!consumoDao.clienteExiste(consumo.getIdCliente())) {
            throw new IllegalArgumentException("Cliente com ID " + consumo.getIdCliente() + " não encontrado.");
        }

        if (!consumoDao.enderecoExiste(consumo.getIdEndereco())) {
            throw new IllegalArgumentException("Endereço com ID " + consumo.getIdEndereco() + " não encontrado.");
        }

        consumoDao.inserirConsumo(consumo);
    }

    public List<Consumo> buscarConsumosPorClienteEEndereco(int idCliente, int idEndereco) throws SQLException {
        if (!consumoDao.clienteExiste(idCliente)) {
            throw new IllegalArgumentException("Cliente com ID " + idCliente + " não encontrado.");
        }

        if (!consumoDao.enderecoExiste(idEndereco)) {
            throw new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado.");
        }

        List<Consumo> consumos = consumoDao.buscarConsumosPorClienteEEndereco(idCliente, idEndereco);
        if (consumos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum consumo encontrado para o cliente e endereço especificados.");
        }
        return consumos;
    }

    public Consumo atualizarConsumoEspecifico(int idCliente, int idEndereco, int idConsumoCli, Consumo consumoAtualizado) throws SQLException {
        if (!consumoDao.clienteExiste(idCliente)) {
            throw new IllegalArgumentException("Cliente com ID " + idCliente + " não encontrado.");
        }

        if (!consumoDao.enderecoExiste(idEndereco)) {
            throw new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado.");
        }

        if (!consumoDao.consumoExistePorId(idConsumoCli, idCliente, idEndereco)) {
            throw new IllegalArgumentException("Consumo com ID " + idConsumoCli + " não encontrado para o cliente e endereço especificados.");
        }

        consumoAtualizado.setIdConsumoCli(idConsumoCli);
        consumoAtualizado.setIdCliente(idCliente);
        consumoAtualizado.setIdEndereco(idEndereco);

        consumoDao.atualizarConsumoEspecifico(idConsumoCli, idCliente, idEndereco, consumoAtualizado);
        return consumoAtualizado;
    }


    public void removerConsumoEspecifico(int idCliente, int idEndereco, int idConsumoCli) throws SQLException {
        if (!consumoDao.clienteExiste(idCliente)) {
            throw new IllegalArgumentException("Cliente com ID " + idCliente + " não encontrado.");
        }

        if (!consumoDao.enderecoExiste(idEndereco)) {
            throw new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado.");
        }

        if (!consumoDao.consumoExistePorId(idConsumoCli, idCliente, idEndereco)) {
            throw new IllegalArgumentException("Consumo com ID " + idConsumoCli + " não encontrado para o cliente e endereço especificados.");
        }

        consumoDao.removerConsumoEspecifico(idConsumoCli, idCliente, idEndereco);
    }
}

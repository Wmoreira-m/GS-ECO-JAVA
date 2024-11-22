package br.com.fiap.eco.bo;

import br.com.fiap.eco.dao.EnderecoDao;
import br.com.fiap.eco.model.Endereco;
import java.sql.SQLException;
import java.util.List;

public class EnderecoBo {

    private final EnderecoDao enderecoDao = new EnderecoDao();

    public void criarEndereco(Endereco endereco) throws SQLException {
        validarEndereco(endereco);

        if (!enderecoDao.verificarClienteExistente(endereco.getIdCliente())) {
            throw new IllegalArgumentException("Cliente com ID " + endereco.getIdCliente() + " não encontrado.");
        }

        enderecoDao.inserirEndereco(endereco);
    }

    public List<Endereco> listarEnderecos() throws SQLException {
        List<Endereco> enderecos = enderecoDao.listar();

        if (enderecos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum endereço encontrado.");
        }

        return enderecos;
    }

    public Endereco buscarEnderecoPorId(int idEndereco) throws SQLException {
        Endereco endereco = enderecoDao.buscarEnderecoPorId(idEndereco);

        if (endereco == null) {
            throw new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado.");
        }

        return endereco;
    }

    public List<Endereco> buscarEnderecosPorCliente(int idCliente) throws SQLException {
        if (!enderecoDao.verificarClienteExistente(idCliente)) {
            throw new IllegalArgumentException("Cliente com ID " + idCliente + " não encontrado.");
        }

        List<Endereco> enderecos = enderecoDao.buscarEnderecosPorCliente(idCliente);

        if (enderecos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum endereço encontrado para o cliente com ID: " + idCliente);
        }

        return enderecos;
    }

    public void atualizarEndereco(int idEndereco, Endereco enderecoAtualizado) throws SQLException {
        validarEndereco(enderecoAtualizado);

        Endereco enderecoExistente = enderecoDao.buscarEnderecoPorId(idEndereco);

        if (enderecoExistente == null) {
            throw new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado.");
        }

        if (!enderecoDao.verificarClienteExistente(enderecoAtualizado.getIdCliente())) {
            throw new IllegalArgumentException("Cliente com ID " + enderecoAtualizado.getIdCliente() + " não encontrado.");
        }

        enderecoAtualizado.setIdEndereco(idEndereco);
        enderecoDao.atualizarEndereco(enderecoAtualizado);
    }

    public void removerEndereco(int idEndereco) throws SQLException {
        Endereco endereco = enderecoDao.buscarEnderecoPorId(idEndereco);

        if (endereco == null) {
            throw new IllegalArgumentException("Endereço com ID " + idEndereco + " não encontrado.");
        }

        enderecoDao.removerEndereco(idEndereco);
    }

    private void validarEndereco(Endereco endereco) {
        if (endereco.getCep() == null || endereco.getCep().isEmpty()) {
            throw new IllegalArgumentException("CEP é obrigatório.");
        }
        if (endereco.getApelidoLocal() == null || endereco.getApelidoLocal().isEmpty()) {
            throw new IllegalArgumentException("Apelido do local é obrigatório.");
        }
        if (endereco.getLocalidade() == null || endereco.getLocalidade().isEmpty()) {
            throw new IllegalArgumentException("Localidade é obrigatória.");
        }
        if (endereco.getUf() == null || endereco.getUf().isEmpty()) {
            throw new IllegalArgumentException("UF é obrigatória.");
        }
        if (endereco.getRegiao() == null || endereco.getRegiao().isEmpty()) {
            throw new IllegalArgumentException("Região é obrigatória.");
        }
        if (endereco.getInsolacaoMd() == null || endereco.getInsolacaoMd().isEmpty()) {
            throw new IllegalArgumentException("Insolação média diária é obrigatória.");
        }
        if (endereco.getIdCliente() <= 0) {
            throw new IllegalArgumentException("ID do cliente deve ser maior que zero.");
        }
    }
}

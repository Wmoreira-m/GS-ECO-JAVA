package br.com.fiap.eco.bo;

import br.com.fiap.eco.dao.ClienteDao;
import br.com.fiap.eco.model.Cliente;
import java.sql.SQLException;
import java.util.List;

public class ClienteBo {

    private ClienteDao clienteDao;

    public ClienteBo() {
        this.clienteDao = new ClienteDao();
    }

    public void inserirCliente(Cliente cliente) throws SQLException {
        clienteDao.inserirCliente(cliente);
    }

    public Cliente buscarClientePorId(int id) throws SQLException {
        return clienteDao.buscarClientePorId(id);
    }

    public Cliente buscarClientePorEmail(String email) throws SQLException {
        return clienteDao.buscarClientePorEmail(email);
    }

    public Cliente buscarClientePorLogin(String email, String senha) throws SQLException {
        return clienteDao.buscarClientePorLogin(email, senha);
    }

    public List<Cliente> listarClientes() throws SQLException {
        return clienteDao.listar();
    }

    public void atualizarCliente(Cliente cliente) throws SQLException {
        clienteDao.atualizarCliente(cliente);
    }

    public void removerCliente(int id) throws SQLException {
        clienteDao.remover(id);
    }
}

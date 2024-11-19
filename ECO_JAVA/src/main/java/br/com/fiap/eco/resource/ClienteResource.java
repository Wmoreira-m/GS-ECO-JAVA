package br.com.fiap.eco.resource;

import br.com.fiap.eco.dao.ClienteDao;
import br.com.fiap.eco.model.Cliente;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private ClienteDao clienteDao = new ClienteDao();


    @POST
    public Response criarCliente(Cliente cliente, @Context UriInfo uriInfo) {
        try {
            clienteDao.inserirCliente(cliente);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(cliente.getIdCliente()));
            return Response.created(builder.build()).entity(cliente).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @GET
    @Path("/{id}")
    public Response buscarClientePorId(@PathParam("id") int id) {
        try {
            Cliente cliente = clienteDao.buscarClientePorId(id);
            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cliente não encontrado. Tente Novamente!")
                        .build();
            }
            return Response.ok(cliente).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response buscarClientePorEmail(@PathParam("email") String email) {
        try {
            Cliente cliente = clienteDao.buscarClientePorEmail(email);
            if (cliente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Cliente não encontrado. Tente Novamente!")
                        .build();
            }
            return Response.ok(cliente).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/login")
    public Response loginCliente(@QueryParam("email") String email, @QueryParam("senha") String senha) {
        try {
            Cliente cliente = clienteDao.buscarClientePorLogin(email, senha);
            return Response.ok(cliente).build();
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            if (errorMessage.equals("Email não encontrado. Tente Novamente!")) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Email não encontrado. Tente Novamente!\"}")
                        .build();
            } else if (errorMessage.equals("Senha incorreta")) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("{\"message\": \"Senha incorreta. Tente Novamente!\"}")
                        .build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro no servidor\"}")
                    .build();
        }
    }


    @GET
    public Response listarClientes() {
        try {
            List<Cliente> clientes = clienteDao.listar();

            if (clientes.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhum cliente encontrado.\"}")
                        .build();
            }

            return Response.ok(clientes).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao buscar clientes: " + e.getMessage() + "\"}")
                    .build();
        }
    }


    @PUT
    @Path("/{id}")
    public Response atualizarCliente(@PathParam("id") int id, Cliente cliente) {
        try {
            cliente.setIdCliente(id);
            clienteDao.atualizarCliente(cliente);
            return Response.ok(cliente).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }


    @DELETE
    @Path("/{id}")
    public Response removerCliente(@PathParam("id") int id) {
        try {
            clienteDao.remover(id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Cliente com id:" + id + "removido com sucesso\"}")
                    .build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}

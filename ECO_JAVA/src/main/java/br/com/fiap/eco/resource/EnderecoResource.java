package br.com.fiap.eco.resource;

import br.com.fiap.eco.dao.EnderecoDao;
import br.com.fiap.eco.model.Endereco;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/endereco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    private EnderecoDao enderecoDao = new EnderecoDao();

    @POST
    public Response criarEndereco(Endereco endereco, @Context UriInfo uriInfo) {
        try {
            enderecoDao.inserirEndereco(endereco);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(endereco.getIdEndereco()));
            return Response.created(builder.build()).entity(endereco).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Erro ao criar endereço: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    public Response listarEnderecos() {
        try {
            List<Endereco> enderecos = enderecoDao.listar();

            if (enderecos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhum endereço encontrado.\"}")
                        .build();
            }
            return Response.ok(enderecos).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao buscar endereços: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarEndereco(@PathParam("id") int id, Endereco endereco) {
        try {

            Endereco enderecoExistente = enderecoDao.buscarEnderecoPorId(id);
            if (enderecoExistente == null) {

                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado\"}")
                        .build();
            }

            endereco.setIdEndereco(id);
            enderecoDao.atualizarEndereco(endereco);

            return Response.ok(endereco).build();
        } catch (SQLException e) {

            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao atualizar endereço\"}")
                    .build();
        }
    }

    @GET
    @Path("/{idCliente}")
    public Response buscarEnderecosPorCliente(@PathParam("idCliente") int idCliente) {
        try {

            boolean clienteExiste = enderecoDao.verificarClienteExistente(idCliente);
            if (!clienteExiste) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente com ID " + idCliente + " não encontrado.\"}")
                        .build();
            }

            List<Endereco> enderecos = enderecoDao.buscarEnderecosPorCliente(idCliente);

            if (enderecos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhum endereço encontrado para o cliente com ID: " + idCliente + "\"}")
                        .build();
            }

            return Response.ok(enderecos).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao buscar endereços do cliente: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeEndereco(@PathParam("id") int id) {
        try {
            Endereco endereco = enderecoDao.buscarEnderecoPorId(id);
            if (endereco == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado\"}")
                        .build();
            }
            enderecoDao.removerEndereco(id);
            return Response.status(Response.Status.NOT_FOUND).entity("Endereço removido com sucesso.").build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao atualizar endereço\"}")
                    .build();
        }
    }
}


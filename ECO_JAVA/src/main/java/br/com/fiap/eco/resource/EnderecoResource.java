package br.com.fiap.eco.resource;

import br.com.fiap.eco.bo.EnderecoBo;
import br.com.fiap.eco.model.Endereco;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/endereco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    private final EnderecoBo enderecoBo = new EnderecoBo();

    @POST
    public Response criarEndereco(Endereco endereco, @Context UriInfo uriInfo) {
        try {
            enderecoBo.criarEndereco(endereco);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(endereco.getIdEndereco()));
            return Response.created(builder.build()).entity(endereco).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao criar endereço: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    public Response listarEnderecos() {
        try {
            List<Endereco> enderecos = enderecoBo.listarEnderecos();
            return Response.ok(enderecos).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao listar endereços: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizarEndereco(@PathParam("id") int id, Endereco endereco) {
        try {
            enderecoBo.atualizarEndereco(id, endereco);
            return Response.ok(endereco).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao atualizar endereço: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/{idCliente}")
    public Response buscarEnderecosPorCliente(@PathParam("idCliente") int idCliente) {
        try {
            List<Endereco> enderecos = enderecoBo.buscarEnderecosPorCliente(idCliente);
            return Response.ok(enderecos).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao buscar endereços do cliente: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removerEndereco(@PathParam("id") int id) {
        try {
            enderecoBo.removerEndereco(id);
            return Response.ok("{\"message\": \"Endereço removido com sucesso.\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao remover endereço: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}

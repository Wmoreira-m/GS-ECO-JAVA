package br.com.fiap.eco.resource;

import br.com.fiap.eco.bo.ConsumoBo;
import br.com.fiap.eco.model.Consumo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/consumos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConsumoResource {

    private final ConsumoBo consumoBO = new ConsumoBo();

    @POST
    public Response criarConsumo(Consumo consumo, @Context UriInfo uriInfo) {
        try {
            consumoBO.criarConsumo(consumo);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(consumo.getIdConsumoCli()));
            return Response.created(builder.build()).entity(consumo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao criar consumo: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @GET
    @Path("/cliente/{idCliente}/endereco/{idEndereco}")
    public Response buscarConsumosPorClienteEEndereco(@PathParam("idCliente") int idCliente, @PathParam("idEndereco") int idEndereco) {
        try {
            List<Consumo> consumos = consumoBO.buscarConsumosPorClienteEEndereco(idCliente, idEndereco);
            return Response.ok(consumos).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao buscar consumos: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/cliente/{idCliente}/endereco/{idEndereco}/consumo/{idConsumoCli}")
    public Response atualizarConsumoEspecifico(@PathParam("idCliente") int idCliente,
                                               @PathParam("idEndereco") int idEndereco,
                                               @PathParam("idConsumoCli") int idConsumoCli,
                                               Consumo consumoAtualizado) {
        try {
            Consumo consumoAtualizadoFinal = consumoBO.atualizarConsumoEspecifico(idCliente, idEndereco, idConsumoCli, consumoAtualizado);
            return Response.ok(consumoAtualizadoFinal).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND    )
                    .entity("{\"message\": \"Erro ao atualizar consumo: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/cliente/{idCliente}/endereco/{idEndereco}/consumo/{idConsumoCli}")
    public Response removerConsumoEspecifico(@PathParam("idCliente") int idCliente,
                                             @PathParam("idEndereco") int idEndereco,
                                             @PathParam("idConsumoCli") int idConsumoCli) {
        try {
            consumoBO.removerConsumoEspecifico(idCliente, idEndereco, idConsumoCli);
            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Consumo removido com sucesso.\"}")
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"" + e.getMessage() + "\"}")
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao remover consumo: " + e.getMessage() + "\"}")
                    .build();
        }
    }
}

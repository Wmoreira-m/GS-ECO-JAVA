package br.com.fiap.eco.resource;

import br.com.fiap.eco.bo.DepoimentoBo;
import br.com.fiap.eco.model.Depoimento;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.sql.SQLException;
import java.util.List;

@Path("/depoimentos")
@Produces(MediaType.APPLICATION_JSON)
public class DepoimentoResource {

    private DepoimentoBo depoimentoBO = new DepoimentoBo();

    @GET
    public Response listarTodos() {
        try {
            List<Depoimento> depoimentos = depoimentoBO.buscarTodos();

            if (depoimentos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhum depoimento encontrado.\"}")
                        .build();
            }

            return Response.ok(depoimentos).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao buscar depoimentos.\"}")
                    .build();
        }
    }

    @POST
    public Response criarDepoimento(Depoimento depoimento, @Context UriInfo uriInfo) {
        try {
            if (!depoimentoBO.existeCliente(depoimento.getIdCliente())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Cliente não encontrado. Tente novamente!")
                        .build();
            }

            depoimentoBO.criarDepoimento(depoimento);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(depoimento.getIdDepoimento()));

            return Response.created(builder.build()).entity(depoimento).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar depoimento: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Erro interno no servidor: " + e.getMessage())
                    .build();
        }
    }
}

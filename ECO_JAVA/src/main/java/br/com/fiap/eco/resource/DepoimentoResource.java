package br.com.fiap.eco.resource;

import br.com.fiap.eco.dao.DepoimentoDao;
import br.com.fiap.eco.model.Depoimento;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/depoimentos")
@Produces(MediaType.APPLICATION_JSON)
public class DepoimentoResource {

    private DepoimentoDao depoimentoDAO = new DepoimentoDao();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        try {
            List<Depoimento> depoimentos = depoimentoDAO.buscarTodos();

            if (depoimentos.isEmpty()) {

                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhum depoimento encontrado.\"}")
                        .build();
            }

            return Response.ok(depoimentos).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"message\": \"Erro ao buscar depoimentos.\"}")
                    .build();
        }
    }

    @POST
    public Response criarDepoimento(Depoimento depoimento, @Context UriInfo uriInfo) {
        try {
            if (depoimento.getDescricao() == null || depoimento.getDescricao().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("A descrição do depoimento é obrigatória.")
                        .build();
            }
            if (!depoimentoDAO.existeCliente(depoimento.getIdCliente())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Cliente não encontrado. Tente novamente!")
                        .build();
            }

            depoimentoDAO.inserirDepoimento(depoimento);

            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(depoimento.getIdDepoimento()));


            return Response.created(builder.build()).entity(depoimento).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao criar depoimento: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro interno no servidor: " + e.getMessage())
                    .build();
        }
    }
}



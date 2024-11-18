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
    public Response listarTodos() {
        try {
            List<Depoimento> depoimentos = depoimentoDAO.buscarTodos();

            if (depoimentos.isEmpty()) {
                return Response.status(Response.Status.OK)
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
}


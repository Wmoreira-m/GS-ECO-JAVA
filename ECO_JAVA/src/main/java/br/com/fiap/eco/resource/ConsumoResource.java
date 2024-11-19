package br.com.fiap.eco.resource;

import br.com.fiap.eco.dao.ConsumoDao;
import br.com.fiap.eco.model.Consumo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;


@Path("/consumos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConsumoResource {

    private ConsumoDao consumoDao = new ConsumoDao();

    @POST
    public Response criarConsumo(Consumo consumo, @Context UriInfo uriInfo) {
        try {

            if (!consumoDao.clienteExiste(consumo.getIdCliente())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente com ID " + consumo.getIdCliente() + " não encontrado.\"}")
                        .build();
            }


            if (!consumoDao.enderecoExiste(consumo.getIdEndereco())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço com ID " + consumo.getIdEndereco() + " não encontrado.\"}")
                        .build();
            }

            consumoDao.inserirConsumo(consumo);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(Integer.toString(consumo.getIdConsumoCli()));

            return Response.created(builder.build()).entity(consumo).build();

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

            boolean clienteExiste = consumoDao.clienteExiste(idCliente);

            if (!clienteExiste) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente não encontrado.\"}")
                        .build();
            }


            boolean enderecoExisteParaCliente = consumoDao.enderecoExiste(idEndereco);

            if (!enderecoExisteParaCliente) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado para o cliente especificado.\"}")
                        .build();
            }


            List<Consumo> consumos = consumoDao.buscarConsumosPorClienteEEndereco(idCliente, idEndereco);


            if (consumos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhum consumo encontrado para o cliente e endereço especificados.\"}")
                        .build();
            }

            return Response.ok(consumos).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Erro ao buscar consumos no banco de dados.\"}")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro inesperado.\"}")
                    .build();
        }
    }


    @PUT
    @Path("/cliente/{idCliente}/endereco/{idEndereco}")
    public Response atualizarConsumo(@PathParam("idCliente") int idCliente, @PathParam("idEndereco") int idEndereco, Consumo consumoAtualizado) {
        try {

            boolean clienteExiste = consumoDao.clienteExiste(idCliente);

            if (!clienteExiste) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente não encontrado.\"}")
                        .build();
            }


            boolean enderecoExisteParaCliente = consumoDao.enderecoExiste(idEndereco);

            if (!enderecoExisteParaCliente) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado para o cliente especificado.\"}")
                        .build();
            }

            if (!consumoDao.consumoExiste(idCliente, idEndereco)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhum consumo encontrado para o cliente e endereço especificados.\"}")
                        .build();
            }

            consumoDao.atualizarConsumo(idCliente, idEndereco, consumoAtualizado);
            return Response.ok(consumoAtualizado).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao atualizar o consumo no banco de dados.\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/cliente/{idCliente}/endereco/{idEndereco}")
    public Response removerConsumo(@PathParam("idCliente") int idCliente, @PathParam("idEndereco") int idEndereco) {
        try {

            if (!consumoDao.clienteExiste(idCliente)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente não encontrado.\"}")
                        .build();
            }


            if (!consumoDao.enderecoExiste(idEndereco)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado.\"}")
                        .build();
            }


            if (!consumoDao.consumoExiste(idCliente, idEndereco)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhum consumo encontrado para o cliente e endereço especificados.\"}")
                        .build();
            }


            consumoDao.removerConsumo(idCliente, idEndereco);

            return Response.status(Response.Status.OK)
                    .entity("{\"message\": \"Consumo removido com sucesso.\"}")
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao remover o consumo.\"}")
                    .build();
        }
    }
}

package br.com.fiap.eco.resource;

import br.com.fiap.eco.bo.ConsumoSimulacaoBo;
import br.com.fiap.eco.model.ConsumoSimulacao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConsumoSimulacaoResource {

    private ConsumoSimulacaoBo simulacaoBO = new ConsumoSimulacaoBo();

    @POST
    public Response criarSimulacao(ConsumoSimulacao simulacao) {
        try {
            if (!simulacaoBO.clienteExiste(simulacao.getIdCliente())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente com ID " + simulacao.getIdCliente() + " não encontrado.\"}")
                        .build();
            }

            if (!simulacaoBO.enderecoExiste(simulacao.getIdEndereco())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço com ID " + simulacao.getIdEndereco() + " não encontrado.\"}")
                        .build();
            }

            if (!simulacaoBO.consumoExiste(simulacao.getIdConsumoCli())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Consumo com ID " + simulacao.getIdConsumoCli() + " não encontrado.\"}")
                        .build();
            }

            simulacaoBO.criarSimulacao(simulacao);

            return Response.status(Response.Status.CREATED)
                    .entity(simulacao)
                    .build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao criar a simulação: " + e.getMessage() + "\"}")
                    .build();
        }
    }

    @PUT
    @Path("/cliente/{idCliente}/endereco/{idEndereco}/simulacao/{idConsumoSimu}")
    public Response atualizarSimulacao(
            @PathParam("idCliente") int idCliente,
            @PathParam("idEndereco") int idEndereco,
            @PathParam("idConsumoSimu") int idConsumoSimu,
            ConsumoSimulacao simulacaoAtualizada) {
        try {
            ConsumoSimulacao simulacaoExistente = simulacaoBO.buscarSimulacaoPorId(idConsumoSimu);
            if (simulacaoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Simulação com ID " + idConsumoSimu + " não encontrada.\"}")
                        .build();
            }

            if (simulacaoExistente.getIdCliente() != idCliente || simulacaoExistente.getIdEndereco() != idEndereco) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"Cliente ou endereço não correspondem à simulação.\"}")
                        .build();
            }

            simulacaoBO.atualizarSimulacao(simulacaoAtualizada, simulacaoExistente);

            return Response.ok(simulacaoExistente).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao atualizar a simulação.\"}")
                    .build();
        }
    }

    @GET
    @Path("/cliente/{idCliente}/endereco/{idEndereco}")
    public Response buscarSimulacoes(
            @PathParam("idCliente") int idCliente,
            @PathParam("idEndereco") int idEndereco) {

        try {
            if (!simulacaoBO.clienteExiste(idCliente)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente não encontrado.\"}")
                        .build();
            }

            if (!simulacaoBO.enderecoExiste(idEndereco)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado.\"}")
                        .build();
            }

            List<ConsumoSimulacao> simulacoes = simulacaoBO.buscarSimulacoes(idCliente, idEndereco);

            if (simulacoes.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Nenhuma simulação encontrada para o cliente e endereço especificados.\"}")
                        .build();
            }
            return Response.ok(simulacoes).build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao buscar simulações.\"}")
                    .build();
        }
    }

    @DELETE
    @Path("/cliente/{idCliente}/endereco/{idEndereco}/simulacao/{idConsumoSimu}")
    public Response deletarSimulacaoEspecifica(
            @PathParam("idCliente") int idCliente,
            @PathParam("idEndereco") int idEndereco,
            @PathParam("idConsumoSimu") int idConsumoSimu) {

        try {
            if (!simulacaoBO.clienteExiste(idCliente)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente não encontrado.\"}")
                        .build();
            }

            if (!simulacaoBO.enderecoExiste(idEndereco)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado.\"}")
                        .build();
            }

            if (!simulacaoBO.simulacaoExiste(idCliente, idEndereco, idConsumoSimu)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Simulação não encontrada.\"}")
                        .build();
            }

            simulacaoBO.removerSimulacaoEspecifica(idCliente, idEndereco, idConsumoSimu);

            return Response.ok("{\"message\": \"Simulação deletada com sucesso.\"}")
                    .build();

        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao deletar a simulação.\"}")
                    .build();
        }
    }
}

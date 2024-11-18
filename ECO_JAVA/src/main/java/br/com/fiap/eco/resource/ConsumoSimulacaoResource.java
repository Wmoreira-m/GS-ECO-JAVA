package br.com.fiap.eco.resource;

import br.com.fiap.eco.dao.ConsumoSimulacaoDao;
import br.com.fiap.eco.model.ConsumoSimulacao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/simulacoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ConsumoSimulacaoResource {

    private ConsumoSimulacaoDao simulacaoDAO = new ConsumoSimulacaoDao();

    @POST
    public Response criarSimulacao(ConsumoSimulacao simulacao) {
        try {
            // Verificar se o cliente existe
            if (!simulacaoDAO.clienteExiste(simulacao.getIdCliente())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente com ID " + simulacao.getIdCliente() + " não encontrado.\"}")
                        .build();
            }

            // Verificar se o endereço existe
            if (!simulacaoDAO.enderecoExiste(simulacao.getIdEndereco())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço com ID " + simulacao.getIdEndereco() + " não encontrado.\"}")
                        .build();
            }

            // Verificar se o consumo existe
            if (!simulacaoDAO.consumoExiste(simulacao.getIdConsumoCli())) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Consumo com ID " + simulacao.getIdConsumoCli() + " não encontrado.\"}")
                        .build();
            }

            // Obter a data do consumo
            String dataConsumo = simulacaoDAO.buscarDataConsumo(simulacao.getIdConsumoCli());

            // Criar a simulação, agora com a data do consumo
            simulacaoDAO.criarSimulacao(simulacao, dataConsumo);

            // Retornar a simulação criada com status CREATED
            return Response.status(Response.Status.CREATED)
                    .entity(simulacao)
                    .build();

        } catch (SQLException e) {
            // Logar o erro para rastrear problemas
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao criar a simulação: " + e.getMessage() + "\"}")
                    .build();
        }
    }


    // Atualizar simulação
    @PUT
    @Path("/cliente/{idCliente}/endereco/{idEndereco}/simulacao/{idConsumoSimu}")
    public Response atualizarSimulacao(
            @PathParam("idCliente") int idCliente,
            @PathParam("idEndereco") int idEndereco,
            @PathParam("idConsumoSimu") int idConsumoSimu,
            ConsumoSimulacao simulacaoAtualizada) {

        try {
            // Verifica se a simulação existe
            ConsumoSimulacao simulacaoExistente = simulacaoDAO.buscarSimulacaoPorId(idConsumoSimu);
            if (simulacaoExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Simulação com ID " + idConsumoSimu + " não encontrada.\"}")
                        .build();
            }

            // Verifica se o cliente e endereço associados existem
            if (simulacaoExistente.getIdCliente() != idCliente || simulacaoExistente.getIdEndereco() != idEndereco) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("{\"message\": \"Cliente ou endereço não correspondem à simulação.\"}")
                        .build();
            }

            if (simulacaoAtualizada.getGrid() != null) {
                simulacaoExistente.setGrid(simulacaoAtualizada.getGrid());
            }

            // Obtém a data de consumo
            String dataConsumo = simulacaoDAO.buscarDataConsumo(simulacaoExistente.getIdConsumoCli());

            // Atualiza a simulação no banco
            simulacaoDAO.atualizarSimulacao(simulacaoExistente, dataConsumo);

            return Response.ok(simulacaoExistente).build();

        } catch (SQLException e) {
            e.printStackTrace();
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
            // Verifica se o cliente existe
            if (!simulacaoDAO.clienteExiste(idCliente)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente não encontrado.\"}")
                        .build();
            }

            // Verifica se o endereço existe
            if (!simulacaoDAO.enderecoExiste(idEndereco)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado.\"}")
                        .build();
            }

            // Busca lista de simulações
            List<ConsumoSimulacao> simulacoes = simulacaoDAO.buscarSimulacoes(idCliente, idEndereco);

            if (simulacoes.isEmpty()) {
                return Response.status(Response.Status.OK)
                        .entity("{\"message\": \"Nenhuma simulação encontrada para o cliente e endereço especificados.\"}")
                        .build();
            }

            return Response.ok(simulacoes).build();

        } catch (SQLException e) {
            e.printStackTrace();
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
            // Verifica se o cliente existe
            if (!simulacaoDAO.clienteExiste(idCliente)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Cliente não encontrado.\"}")
                        .build();
            }

            // Verifica se o endereço existe
            if (!simulacaoDAO.enderecoExiste(idEndereco)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Endereço não encontrado.\"}")
                        .build();
            }

            // Verifica se a simulação específica existe
            if (!simulacaoDAO.simulacaoExiste(idCliente, idEndereco, idConsumoSimu)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"message\": \"Simulação não encontrada.\"}")
                        .build();
            }

            // Remove a simulação específica
            simulacaoDAO.removerSimulacaoEspecifica(idCliente, idEndereco, idConsumoSimu);

            return Response.ok("{\"message\": \"Simulação deletada com sucesso.\"}")
                    .build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"message\": \"Erro ao deletar a simulação.\"}")
                    .build();
        }
    }
}

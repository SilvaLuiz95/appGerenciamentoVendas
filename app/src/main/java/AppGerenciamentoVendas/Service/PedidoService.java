package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.Pedido;
import AppGerenciamentoVendas.Repository.ClienteRepository;
import AppGerenciamentoVendas.Repository.PedidoRepository;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class PedidoService {

    private PedidoRepository pedidoRepository = new PedidoRepository();
    private ClienteRepository clienteRepository = new ClienteRepository();

    /**
     * Salva um pedido no banco de dados.
     *
     * <p>
     * Este método insere um novo pedido no banco de dados utilizando o
     * repositório de pedidos.</p>
     *
     * @param pedido O objeto {@code Pedido} que contém as informações do pedido
     * a ser salvo.
     * @return O objeto {@code Pedido} que foi salvo, com o ID atualizado, caso
     * seja gerado automaticamente.
     */
    public Pedido salvarPedido(Pedido pedido) {
        return new PedidoRepository().insert(pedido);
    }

    /**
     * Retorna o próximo ID disponível para um pedido.
     *
     * <p>
     * Este método consulta o repositório de pedidos para obter o ID mais
     * recente e retorna o próximo valor disponível.</p>
     *
     * @return O próximo ID disponível para um novo pedido.
     */
    public Long carregarId() {
        return pedidoRepository.selectId();
    }

    /**
     * Consulta os pedidos visualizando por cliente.
     *
     * <p>
     * Este método realiza uma consulta para buscar informações sobre pedidos
     * associados a um ou mais clientes, podendo filtrar por situação, data de
     * início, data de fim, e opcionalmente por produto.</p>
     *
     * @param situacao A situação do pedido a ser filtrada (ex: "ATIVO",
     * "EXCLUIDO").
     * @param dataInicio A data de início do período de consulta.
     * @param dataFim A data de fim do período de consulta.
     * @param idCliente O ID do cliente a ser consultado (pode ser
     * {@code null}).
     * @param idProduto O ID do produto a ser consultado (pode ser
     * {@code null}).
     * @return Uma lista de arrays de objetos, onde cada array representa uma
     * linha do resultado da consulta.
     * @throws Exception Se a data de início for posterior à data de fim.
     */
    public List<Object[]> consultaPedidosVisualizacaoPorCliente(String situacao, LocalDate dataInicio, LocalDate dataFim, Long idCliente, Long idProduto) throws Exception {
        if (dataInicio.isAfter(dataFim)) {
            throw new Exception("A data de inicio deve ser menor ou igual à data de fim");
        }
        return pedidoRepository.consultaPedidosVisualizacaoPorCliente(situacao, dataInicio, dataFim, idCliente, idProduto);
    }

    /**
     * Consulta pedidos de visualização por produto.
     *
     * <p>
     * Este método realiza uma consulta para buscar informações sobre pedidos
     * associados a um ou mais produtos, filtrados por situação, data de
     * início, data de fim, e opcionalmente por cliente.</p>
     *
     * @param situacao A situação do pedido a ser filtrada (ex: "ATIVO",
     * "EXCLUIDO").
     * @param dataInicio A data de início do período de consulta.
     * @param dataFim A data de fim do período de consulta.
     * @param idCliente O ID do cliente a ser consultado (pode ser
     * {@code null}).
     * @param idProduto O ID do produto a ser consultado (pode ser
     * {@code null}).
     * @return Uma lista de arrays de objetos, onde cada array representa uma
     * linha do resultado da consulta.
     * @throws Exception Se a data de início for posterior à data de fim.
     */
    public List<Object[]> consultaPedidosVisualizacaoPorProduto(String situacao, LocalDate dataInicio, LocalDate dataFim, Long idCliente, Long idProduto) throws Exception {
        if (dataInicio.isAfter(dataFim)) {
            throw new Exception("A data de inicio deve ser menor ou igual à data de fim");
        }
        return pedidoRepository.consultaPedidosVisualizacaoPorProduto(situacao, dataInicio, dataFim, idCliente, idProduto);
    }

}

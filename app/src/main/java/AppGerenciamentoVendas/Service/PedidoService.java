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
     * Este m�todo insere um novo pedido no banco de dados utilizando o
     * reposit�rio de pedidos.</p>
     *
     * @param pedido O objeto {@code Pedido} que cont�m as informa��es do pedido
     * a ser salvo.
     * @return O objeto {@code Pedido} que foi salvo, com o ID atualizado, caso
     * seja gerado automaticamente.
     */
    public Pedido salvarPedido(Pedido pedido) {
        return new PedidoRepository().insert(pedido);
    }

    /**
     * Retorna o pr�ximo ID dispon�vel para um pedido.
     *
     * <p>
     * Este m�todo consulta o reposit�rio de pedidos para obter o ID mais
     * recente e retorna o pr�ximo valor dispon�vel.</p>
     *
     * @return O pr�ximo ID dispon�vel para um novo pedido.
     */
    public Long carregarId() {
        return pedidoRepository.selectId();
    }

    /**
     * Consulta os pedidos visualizando por cliente.
     *
     * <p>
     * Este m�todo realiza uma consulta para buscar informa��es sobre pedidos
     * associados a um ou mais clientes, podendo filtrar por situa��o, data de
     * in�cio, data de fim, e opcionalmente por produto.</p>
     *
     * @param situacao A situa��o do pedido a ser filtrada (ex: "ATIVO",
     * "EXCLUIDO").
     * @param dataInicio A data de in�cio do per�odo de consulta.
     * @param dataFim A data de fim do per�odo de consulta.
     * @param idCliente O ID do cliente a ser consultado (pode ser
     * {@code null}).
     * @param idProduto O ID do produto a ser consultado (pode ser
     * {@code null}).
     * @return Uma lista de arrays de objetos, onde cada array representa uma
     * linha do resultado da consulta.
     * @throws Exception Se a data de in�cio for posterior � data de fim.
     */
    public List<Object[]> consultaPedidosVisualizacaoPorCliente(String situacao, LocalDate dataInicio, LocalDate dataFim, Long idCliente, Long idProduto) throws Exception {
        if (dataInicio.isAfter(dataFim)) {
            throw new Exception("A data de inicio deve ser menor ou igual � data de fim");
        }
        return pedidoRepository.consultaPedidosVisualizacaoPorCliente(situacao, dataInicio, dataFim, idCliente, idProduto);
    }

    /**
     * Consulta pedidos de visualiza��o por produto.
     *
     * <p>
     * Este m�todo realiza uma consulta para buscar informa��es sobre pedidos
     * associados a um ou mais produtos, filtrados por situa��o, data de
     * in�cio, data de fim, e opcionalmente por cliente.</p>
     *
     * @param situacao A situa��o do pedido a ser filtrada (ex: "ATIVO",
     * "EXCLUIDO").
     * @param dataInicio A data de in�cio do per�odo de consulta.
     * @param dataFim A data de fim do per�odo de consulta.
     * @param idCliente O ID do cliente a ser consultado (pode ser
     * {@code null}).
     * @param idProduto O ID do produto a ser consultado (pode ser
     * {@code null}).
     * @return Uma lista de arrays de objetos, onde cada array representa uma
     * linha do resultado da consulta.
     * @throws Exception Se a data de in�cio for posterior � data de fim.
     */
    public List<Object[]> consultaPedidosVisualizacaoPorProduto(String situacao, LocalDate dataInicio, LocalDate dataFim, Long idCliente, Long idProduto) throws Exception {
        if (dataInicio.isAfter(dataFim)) {
            throw new Exception("A data de inicio deve ser menor ou igual � data de fim");
        }
        return pedidoRepository.consultaPedidosVisualizacaoPorProduto(situacao, dataInicio, dataFim, idCliente, idProduto);
    }

}

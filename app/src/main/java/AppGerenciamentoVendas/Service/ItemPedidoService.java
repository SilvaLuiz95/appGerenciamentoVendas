package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.ItemPedido;
import AppGerenciamentoVendas.Repository.ItemPedidoRepository;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class ItemPedidoService {

    private ItemPedidoRepository itemPedidoRepository;

    /**
     * Construtor da classe ItemPedidoService.
     *
     * <p>
     * Este construtor inicializa uma instância do repositório
     * {@code ItemPedidoRepository}, que será usada para manipular operações
     * relacionadas a itens de pedido no banco de dados.</p>
     */
    public ItemPedidoService() {
        this.itemPedidoRepository = new ItemPedidoRepository();
    }

    /**
     * Adiciona uma lista de itens de pedido ao banco de dados.
     *
     * <p>
     * Este método recebe uma lista de objetos {@code ItemPedido} e delega a
     * inserção desses itens ao repositório, que é responsável por realizar a
     * operação de inserção no banco de dados.</p>
     *
     * @param itensPedido Uma lista de objetos {@code ItemPedido} que
     * representam os itens de pedido a serem adicionados.
     * @throws Exception Se ocorrer algum erro durante o processo de inserção
     * dos itens de pedido.
     */
    public void adicionarItensPedido(List<ItemPedido> itensPedido) throws Exception {
        itemPedidoRepository.inserirItensPedido(itensPedido);
    }

}

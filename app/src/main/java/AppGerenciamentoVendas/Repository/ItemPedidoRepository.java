package AppGerenciamentoVendas.Repository;

import AppGerenciamentoVendas.Model.ItemPedido;
import AppGerenciamentoVendas.Util.ConexaoBanco;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class ItemPedidoRepository {

    public int inserirItemPedido(ItemPedido itemPedido){
        String query = String.format("""
                                   INSERT INTO item_pedido (id_pedido, id_produto, quantidade, valor_total) 
                                   VALUES (%d, %d, %d, '%s')""",
                                   itemPedido.getPedido().getId(),
                                   itemPedido.getProduto().getId(),
                                   itemPedido.getQuantidade(),
                                   itemPedido.getValorTotal());

        try (Statement stmt = ConexaoBanco.getConn().createStatement()) {
            return stmt.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao inserir item no pedido", e);
        }
    }

    public void inserirItensPedido(List<ItemPedido> itensPedido) throws Exception {
        for (ItemPedido item : itensPedido) {
            inserirItemPedido(item);
        }
    }

}

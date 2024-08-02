package AppGerenciamentoVendas.Repository;

import AppGerenciamentoVendas.Model.Pedido;
import AppGerenciamentoVendas.Util.ConexaoBanco;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class PedidoRepository {

    public Pedido insert(Pedido pedido) {
        String query = String.format("""
                                  INSERT INTO pedido (id_cliente, data, valor_total, status_pedido)
                                  VALUES (%d, '%s', '%s','%s');
                                  """, pedido.getCliente().getId(),
                pedido.getData(), pedido.getValorTotal(), pedido.getStatusPedido());
        try (Statement stmt = ConexaoBanco.getConn().createStatement()) {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Pedido();
    }

    public Long selectId() {
        String query = String.format("""
                    SELECT MAX(id) AS ultimo_id FROM pedido
                    """);

        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                Long ultimoId = rs.getLong("ultimo_id");
                if (ultimoId != null) {
                    return ultimoId;
                } else {
                    return 0L;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

    public BigDecimal totalPedidoCliente(Long idCliente, LocalDate dataFechamento) {
        String query = String.format("""
                                     SELECT COALESCE(SUM(valor_total), 0) AS totalCliente
                                     FROM pedido 
                                     WHERE id_cliente = %d
                                     AND data >= '%s' 
                                     """, idCliente, dataFechamento);
        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getBigDecimal("totalCliente");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Object[]> consultaPedidosVisualizacaoPorCliente(String situacao, LocalDate dataInicio, LocalDate dataFim, Long idCliente, Long idProduto) throws Exception {
        String query = String.format("""
                                     SELECT c.id, c.nome, SUM(p.valor_total) AS valor_total_pedidos
                                     FROM pedido p
                                     INNER JOIN cliente c ON p.id_cliente = c.id
                                     INNER JOIN item_pedido ip ON p.id = ip.id_pedido
                                     WHERE p.status_pedido = '%s'
                                     %s
                                     %s
                                     %s
                                     GROUP BY c.id, c.nome                                     
                                     """, situacao,
                (dataInicio != null && dataFim != null) ? String.format("AND p.data BETWEEN '%s' AND '%s'", dataInicio, dataFim) : "",
                (idCliente != null) ? String.format("AND c.id = %d", idCliente) : "",
                (idProduto != null) ? String.format("AND ip.id_produto = %d", idProduto) : "");
        List<Object[]> clientes = new ArrayList<>();
        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] cliente = new Object[3];
                cliente[0] = rs.getLong("id");
                cliente[1] = rs.getString("nome");
                cliente[2] = rs.getLong("valor_total_pedidos");
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public List<Object[]> consultaPedidosVisualizacaoPorProduto(String situacao, LocalDate dataInicio, LocalDate dataFim, Long idCliente, Long idProduto) throws Exception {
        String query = String.format("""
                                     SELECT pr.id, pr.descricao, SUM(ip.valor_total) AS valor_total_saidas
                                     FROM item_pedido ip
                                     JOIN produto pr ON ip.id_produto = pr.id
                                     JOIN pedido p ON ip.id_pedido = p.id
                                     WHERE p.status_pedido = '%s' 
                                     %s
                                     %s
                                     %s
                                     GROUP BY pr.id, pr.descricao                                     
                                     """, situacao,
                (dataInicio != null && dataFim != null) ? String.format("AND p.data BETWEEN '%s' AND '%s'", dataInicio, dataFim) : "",
                (idCliente != null) ? String.format("AND p.id_cliente = %d", idCliente) : "",
                (idProduto != null) ? String.format("AND ip.id_produto = %d", idProduto) : "");
        List<Object[]> produtos = new ArrayList<>();
        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            int i = 0;
            while (rs.next()) {
                Object[] produto = new Object[3];
                produto[0] = rs.getLong("id");
                produto[1] = rs.getString("descricao");
                produto[2] = rs.getLong("valor_total_saidas");
                produtos.add(produto);
            }
        }
        return produtos;
    }

}

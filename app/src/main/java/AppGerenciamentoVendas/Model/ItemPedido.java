package AppGerenciamentoVendas.Model;

import java.math.BigDecimal;

/**
 *
 * @author Luiz
 */
public class ItemPedido {

    private Long id;
    private Pedido pedido;
    private Produto produto;
    private Integer quantidade;
    private BigDecimal valorTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public ItemPedido() {
    }

    public ItemPedido(Long id, Pedido pedido, Integer quantidade, BigDecimal valorTotal) {
        this.id = id;
        this.pedido = pedido;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    public ItemPedido(Long id, Pedido pedido, Produto produto, Integer quantidade, BigDecimal valorTotal) {
        this.id = id;
        this.pedido = pedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

}

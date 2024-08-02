package AppGerenciamentoVendas.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Luiz
 */
public class Pedido {

    private Long id;
    private Cliente cliente;
    private LocalDate data;
    private BigDecimal valorTotal;
    private StatusPedido statusPedido = StatusPedido.ATIVO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Pedido() {
    }

    public Pedido(Long id) {
        this.id = id;
    }
    
    public Pedido(Long id, Cliente cliente, LocalDate data,  BigDecimal valorTotal) {
        this.id = id;
        this.cliente = cliente;
        this.data = data;
        this.valorTotal = valorTotal;
    }

}

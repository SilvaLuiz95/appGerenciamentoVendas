package AppGerenciamentoVendas.Model;

import java.math.BigDecimal;

/**
 *
 * @author Luiz
 */
public class Cliente {
    
    private Long id;
    private String nome;
    private BigDecimal limiteCompra;
    private Integer diaFechamento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getLimiteCompra() {
        return limiteCompra;
    }

    public void setLimiteCompra(BigDecimal limiteCompra) {
        this.limiteCompra = limiteCompra;
    }

    public Integer getDiaFechamento() {
        return diaFechamento;
    }

    public void setDiaFechamento(Integer diaFechamento) {
        this.diaFechamento = diaFechamento;
    }

    public Cliente() {
    }

    public Cliente(Long id) {
        this.id = id;
    }

    public Cliente(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Cliente(Long id, String nome, BigDecimal limiteCompra, Integer diaFechamento) {
        this.id = id;
        this.nome = nome;
        this.limiteCompra = limiteCompra;
        this.diaFechamento = diaFechamento;
    }
    
}

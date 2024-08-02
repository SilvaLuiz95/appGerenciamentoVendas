package AppGerenciamentoVendas.Repository;

import AppGerenciamentoVendas.Model.Cliente;
import AppGerenciamentoVendas.Util.ConexaoBanco;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class ClienteRepository {

    
    public List<Cliente> select() {
        List<Cliente> listaClientes = new ArrayList<>();

        String query = """
                       SELECT * FROM cliente
                       ORDER by id ASC;
                       """;

        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setLimiteCompra(rs.getBigDecimal("limite_compra"));
                cliente.setDiaFechamento(rs.getInt("dia_fechamento"));

                listaClientes.add(cliente);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaClientes;
    }

    public Cliente select(Long id) {
        String query = String.format("""
                       SELECT * FROM cliente
                       WHERE id = %d;
                       """, id);
        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setLimiteCompra(rs.getBigDecimal("limite_compra"));
                cliente.setDiaFechamento(rs.getInt("dia_fechamento"));

                return cliente;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Cliente insert(String nome, BigDecimal limiteCompra, Integer diaFechamento) {
        String query = String.format("""
                                  INSERT INTO cliente (nome, limite_compra, dia_fechamento)
                                  VALUES ('%s', '%s',%d)
                                  """, nome, limiteCompra.toPlainString(), diaFechamento);
        try (Statement stmt = ConexaoBanco.getConn().createStatement()) {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Cliente();
    }

    public Cliente update(Long id, String nome, BigDecimal limiteCompra, Integer diaFechamento) {
        String query = String.format("""
                                  UPDATE cliente
                                  SET nome = '%s', limite_compra = '%s', dia_fechamento = %d
                                  WHERE id = %d;
                                  """, nome, limiteCompra.toPlainString(), diaFechamento, id);
        try (Statement stmt = ConexaoBanco.getConn().createStatement()) {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Cliente();
    }

    public Long delete(Long id) {
        String query = String.format("""
                                  DELETE FROM cliente
                                  WHERE id = %d;
                                  """, id);
        try (Statement stmt = ConexaoBanco.getConn().createStatement();) {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Long selectId() {
        String query = String.format("""
                    SELECT MAX(id) AS ultimo_id FROM cliente
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

}

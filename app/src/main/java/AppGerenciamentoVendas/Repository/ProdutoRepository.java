package AppGerenciamentoVendas.Repository;

import AppGerenciamentoVendas.Model.Produto;
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
public class ProdutoRepository {

    public List<Produto> select() {
        List<Produto> listaProdutos = new ArrayList<>();

        String query = """
                       SELECT * FROM produto
                       ORDER by id ASC;
                       """;

        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getLong("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getBigDecimal("preco"));

                listaProdutos.add(produto);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaProdutos;
    }

    public Produto select(Long id) {
        String query = String.format("""
                       SELECT * FROM produto
                       WHERE id = %d;
                       """, id);

        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getLong("id"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getBigDecimal("preco"));

                return produto;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Produto insert(String descricao, BigDecimal preco) {
        String query = String.format("""
                                  INSERT INTO produto (descricao, preco)
                                  VALUES ('%s', '%s')
                                  """, descricao, preco.toPlainString());
        try (Statement stmt = ConexaoBanco.getConn().createStatement()) {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Produto();
    }

    public Produto update(Long id, String descricao, BigDecimal preco) {
        String query = String.format("""
                                  UPDATE produto 
                                  SET descricao = '%s', preco = '%s'
                                  WHERE id = %d;
                                  """, descricao, preco.toPlainString(), id);
        try (Statement stmt = ConexaoBanco.getConn().createStatement()) {
            stmt.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Produto();
    }

    public Long delete(Long id) {
        String query = String.format("""
                                  DELETE FROM produto
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
                    SELECT MAX(id) AS ultimo_id FROM produto
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

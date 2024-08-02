package AppGerenciamentoVendas.Repository;

import AppGerenciamentoVendas.Model.Usuario;
import AppGerenciamentoVendas.Util.ConexaoBanco;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Luiz
 */
public class UsuarioRepository {

    public void cadastrarUsuario(Usuario usuario) throws Exception {
        String query = String.format("""
                                         INSERT INTO usuario (usuario, senha)
                                         VALUE
                                         """, usuario.getUsuario(), usuario.getSenha());

        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {
        } catch (Exception e) {
        }
    }

    public Usuario validacaoUsuario(String usuario, String senha) {
        String query = String.format("""
                                     SELECT * FROM usuario
                                     WHERE usuario = '%s' 
                                     AND senha = '%s'
                                     """, usuario, senha);

        try (Statement stmt = ConexaoBanco.getConn().createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                Usuario usuarioValidacao = new Usuario();

                usuarioValidacao.setId(rs.getLong("id"));
                usuarioValidacao.setUsuario(rs.getString("usuario"));
                usuarioValidacao.setSenha(rs.getString("senha"));

                return usuarioValidacao;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}

package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.Usuario;
import AppGerenciamentoVendas.Repository.UsuarioRepository;

/**
 *
 * @author Luiz
 */
public class UsuarioService {

    /**
     * Cadastra um novo usu�rio no sistema.
     *
     * @param usuario Um objeto {@link Usuario} contendo as informa��es do
     * usu�rio a ser cadastrado.
     * @throws Exception Se ocorrer um erro ao tentar cadastrar o usu�rio, como
     * problemas com o reposit�rio.
     */
    public void cadastrasUsuario(Usuario usuario) throws Exception {
        new UsuarioRepository().cadastrarUsuario(usuario);
    }

    /**
     * Valida as credenciais de um usu�rio, verificando se o nome de usu�rio e a
     * senha correspondem a um usu�rio existente no sistema.
     *
     * @param usuario O nome de usu�rio a ser validado.
     * @param senha A senha do usu�rio a ser validada.
     * @return Um objeto {@link Usuario} correspondente ao usu�rio validado.
     * @throws Exception Se o nome de usu�rio ou a senha estiverem incorretos,
     * ou se ocorrer um erro ao tentar validar o usu�rio.
     */
    public Usuario validacaoUsuario(String usuario, String senha) throws Exception {
        Usuario usuarioValidado = new UsuarioRepository().validacaoUsuario(usuario, senha);

        if (usuarioValidado != null) {

            return usuarioValidado;
        } else {
            throw new Exception("Usu�rio ou senha inv�lidos");
        }
    }
}

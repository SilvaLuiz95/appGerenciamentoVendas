package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.Usuario;
import AppGerenciamentoVendas.Repository.UsuarioRepository;

/**
 *
 * @author Luiz
 */
public class UsuarioService {

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param usuario Um objeto {@link Usuario} contendo as informações do
     * usuário a ser cadastrado.
     * @throws Exception Se ocorrer um erro ao tentar cadastrar o usuário, como
     * problemas com o repositório.
     */
    public void cadastrasUsuario(Usuario usuario) throws Exception {
        new UsuarioRepository().cadastrarUsuario(usuario);
    }

    /**
     * Valida as credenciais de um usuário, verificando se o nome de usuário e a
     * senha correspondem a um usuário existente no sistema.
     *
     * @param usuario O nome de usuário a ser validado.
     * @param senha A senha do usuário a ser validada.
     * @return Um objeto {@link Usuario} correspondente ao usuário validado.
     * @throws Exception Se o nome de usuário ou a senha estiverem incorretos,
     * ou se ocorrer um erro ao tentar validar o usuário.
     */
    public Usuario validacaoUsuario(String usuario, String senha) throws Exception {
        Usuario usuarioValidado = new UsuarioRepository().validacaoUsuario(usuario, senha);

        if (usuarioValidado != null) {

            return usuarioValidado;
        } else {
            throw new Exception("Usuário ou senha inválidos");
        }
    }
}

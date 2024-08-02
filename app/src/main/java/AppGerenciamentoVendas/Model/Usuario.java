package AppGerenciamentoVendas.Model;

/**
 *
 * @author Luiz
 */
public class Usuario {

    private Long id;
    private String usuario;
    private String senha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario() {
    }

    public Usuario(Long id, String usuario, String senha, Boolean situacao) {
        this.id = id;
        this.usuario = usuario;
        this.senha = senha;
    }
}

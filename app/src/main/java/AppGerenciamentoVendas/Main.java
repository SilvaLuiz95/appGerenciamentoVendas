package AppGerenciamentoVendas;

import AppGerenciamentoVendas.Util.ConexaoBanco;
import AppGerenciamentoVendas.View.Login.LoginView;

public class Main {
    
    public static void main(String[] args) {
        ConexaoBanco.iniciarBanco("localhost", "5432", "AppGerenciamentoVendas", "postgres", "postgres");
        
        LoginView form = new LoginView();
        form.setVisible(true);
    }
}

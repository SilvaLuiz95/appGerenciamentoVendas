package AppGerenciamentoVendas.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luiz
 */
public class ConexaoBanco {
    
    private static Connection conn;
    
        public static void iniciarBanco(String host, String porta, String banco, String usuario, String senha) {
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://" + host + ":" + porta + "/" + banco, usuario, senha);
        } catch (SQLException ex) {
            throw new RuntimeException("Não foi possível se conectar com o Banco de Dados", ex);
        }
    }

    public static Connection getConn() {
        return conn;
    }
}

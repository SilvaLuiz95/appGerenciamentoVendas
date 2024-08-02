package AppGerenciamentoVendas.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author Luiz
 */
public class Util {

        public static String getDataAtual() throws Exception {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").format(new Date());

        } catch (Exception ex) {
            return "";
        }
    }

}

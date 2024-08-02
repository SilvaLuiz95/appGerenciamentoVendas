package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.ItemPedido;
import AppGerenciamentoVendas.Model.Pedido;
import AppGerenciamentoVendas.Repository.ItemPedidoRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Luiz
 */
public class ItemPedidoServiceTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private ItemPedidoService itemPedidoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdicionarItensPedidoValido() throws Exception {

        Pedido pedido1 = new Pedido(1L); // Suponha que o construtor de Pedido seja esse
        Pedido pedido2 = new Pedido(2L);

        List<ItemPedido> itensPedido = Arrays.asList(
                new ItemPedido(1L, pedido1, 5, new BigDecimal("10.0")),
                new ItemPedido(2L, pedido2, 3, new BigDecimal("15.0"))
        );

        itemPedidoService.adicionarItensPedido(itensPedido);

        verify(itemPedidoRepository, Mockito.times(1)).inserirItensPedido(itensPedido);

    }

    @Test
    public void testAdicionarItensPedidoException() throws Exception {
        Pedido pedido1 = new Pedido(1L);
        Pedido pedido2 = new Pedido(2L);

        List<ItemPedido> itensPedido = Arrays.asList(
                new ItemPedido(1L, pedido1, 5, new BigDecimal("10.0")),
                new ItemPedido(2L, pedido2, 3, new BigDecimal("15.0"))
        );

        Mockito.doThrow(new Exception("Erro ao adicionar itens")).when(itemPedidoRepository).inserirItensPedido(itensPedido);

        Exception exception = assertThrows(Exception.class, () -> {
            itemPedidoService.adicionarItensPedido(itensPedido);
        });

        assertEquals("Erro ao adicionar itens", exception.getMessage());
    }
}

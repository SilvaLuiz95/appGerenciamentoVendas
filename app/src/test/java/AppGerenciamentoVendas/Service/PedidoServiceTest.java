package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.Pedido;
import AppGerenciamentoVendas.Repository.PedidoRepository;
import java.time.LocalDate;
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
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSalvarPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        Mockito.when(pedidoRepository.insert(pedido)).thenReturn(pedido);

        Pedido resultado = pedidoService.salvarPedido(pedido);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(pedidoRepository).insert(pedido);
    }

    @Test
    public void testCarregarId() {
        Mockito.when(pedidoRepository.selectId()).thenReturn(1L);

        Long resultado = pedidoService.carregarId();

        assertEquals(1L, resultado);
        verify(pedidoRepository).selectId();
    }

    @Test
    public void testConsultaPedidosVisualizacaoPorClienteValido() throws Exception {
        LocalDate dataInicio = LocalDate.of(2024, 1, 1);
        LocalDate dataFim = LocalDate.of(2024, 12, 31);
        Long idCliente = 1L;
        Long idProduto = null;

        List<Object[]> resultadoEsperado = Arrays.asList(
                new Object[]{1L, "Pedido1", "Detalhes1"},
                new Object[]{2L, "Pedido2", "Detalhes2"}
        );

        Mockito.when(pedidoRepository.consultaPedidosVisualizacaoPorCliente("ATIVO", dataInicio, dataFim, idCliente, idProduto))
                .thenReturn(resultadoEsperado);

        List<Object[]> resultado = pedidoService.consultaPedidosVisualizacaoPorCliente("ATIVO", dataInicio, dataFim, idCliente, idProduto);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        for (int i = 0; i < resultado.size(); i++) {
            assertArrayEquals(resultadoEsperado.get(i), resultado.get(i));
        }
        verify(pedidoRepository).consultaPedidosVisualizacaoPorCliente("ATIVO", dataInicio, dataFim, idCliente, idProduto);
    }

    @Test
    public void testConsultaPedidosVisualizacaoPorProdutoInavalido() {
        LocalDate dataInicio = LocalDate.of(2024, 12, 31);
        LocalDate dataFim = LocalDate.of(2024, 1, 1);

        Exception exception = assertThrows(Exception.class, () -> {
            pedidoService.consultaPedidosVisualizacaoPorProduto("ATIVO", dataInicio, dataFim, null, null);
        });

        assertEquals("A data de inicio deve ser menor ou igual à data de fim", exception.getMessage());
    }

}

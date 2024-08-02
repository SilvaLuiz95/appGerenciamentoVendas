package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.Cliente;
import AppGerenciamentoVendas.Repository.ClienteRepository;
import AppGerenciamentoVendas.Service.ClienteService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.TableModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Luiz
 */
public class ClienteServiceTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        clienteService = new ClienteService();
    }

    public ClienteServiceTest() {
    }

    @Test
    public void testCarregarListaClientes() {
        clienteRepository = new ClienteRepository() {
            @Override
            public List<Cliente> select() {
                return Arrays.asList(
                        new Cliente(1L, "Cliente A"),
                        new Cliente(2L, "Cliente B")
                );
            }
        };
        clienteService.setClienteRepository(clienteRepository);
        List<Cliente> result = clienteService.carregarListaClientes();
        assertEquals(2, result.size());
    }

    @Test
    public void testCarregarTabelaCodigoENome() {
        List<Cliente> clientesMock = Arrays.asList(
                new Cliente(1L, "Cliente A"),
                new Cliente(2L, "Cliente B")
        );

        Mockito.when(clienteRepository.select()).thenReturn(clientesMock);

        TableModel tableModel = clienteService.carregarTabelaCodigoENome();

        assertEquals(2, tableModel.getRowCount(), "O número de linhas no TableModel deve ser igual ao número de clientes.");
        assertEquals(2, tableModel.getColumnCount(), "O TableModel deve ter 2 colunas.");

        assertEquals(1L, tableModel.getValueAt(0, 0), "O valor da coluna 'Codigo' na primeira linha deve ser 1.");
        assertEquals("Cliente A", tableModel.getValueAt(0, 1), "O valor da coluna 'Nome' na primeira linha deve ser 'Cliente A'.");

        assertEquals(2L, tableModel.getValueAt(1, 0), "O valor da coluna 'Codigo' na segunda linha deve ser 2.");
        assertEquals("Cliente B", tableModel.getValueAt(1, 1), "O valor da coluna 'Nome' na segunda linha deve ser 'Cliente B'.");

        assertEquals(false, tableModel.isCellEditable(0, 0), "As células do TableModel devem ser não editáveis.");
        assertEquals(false, tableModel.isCellEditable(1, 1), "As células do TableModel devem ser não editáveis.");
    }

    @Test
    public void testCarregaClienteExistente() {
        Cliente clienteMock = new Cliente(1L, "Cliente A");

        Mockito.when(clienteRepository.select(1L)).thenReturn(clienteMock);

        Cliente clienteRetornado = clienteService.carregaCliente(1L);

        assertEquals(clienteMock, clienteRetornado, "O cliente retornado deve ser igual ao cliente mockado.");
    }

    @Test
    public void testCarregaClienteNaoExistente() {
        Mockito.when(clienteRepository.select(99L)).thenReturn(null);

        Cliente clienteRetornado = clienteService.carregaCliente(99L);

        assertNull(clienteRetornado, "O cliente retornado deve ser null para um ID de cliente não existente.");
    }

    @Test
    public void testCarregarIdComIdNull() {
        Mockito.when(clienteRepository.selectId()).thenReturn(null);

        Long idRetornado = clienteService.carregarId();

        assertEquals(1L, idRetornado, "Quando não há clientes registrados, o próximo ID deve ser 1.");
    }

    @Test
    public void testCarregarIdComIdExistente() {
        Mockito.when(clienteRepository.selectId()).thenReturn(5L);

        Long idRetornado = clienteService.carregarId();

        assertEquals(6L, idRetornado, "Quando há clientes registrados, o próximo ID deve ser o maior ID + 1.");
    }

    @Test
    public void testCadastrarClienteDiaInvalido() throws Exception {
        String nome = "Cliente Teste";
        BigDecimal limiteCompra = BigDecimal.valueOf(1000);
        Integer diaFechamento = 32;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.cadastrarCliente(nome, limiteCompra, diaFechamento);
        });

        assertEquals("A data de fechamento deve ser entre 1 e 31!", exception.getMessage());
    }

    @Test
    public void testCadastrarClienteDadosValidos() throws Exception {
        String nome = "Cliente Teste";
        BigDecimal limiteCompra = BigDecimal.valueOf(1000);
        Integer diaFechamento = 15;
        Cliente clienteEsperado = new Cliente(1L, nome);

        Mockito.when(clienteRepository.insert(nome, limiteCompra, diaFechamento)).thenReturn(clienteEsperado);

        Cliente clienteRetornado = clienteService.cadastrarCliente(nome, limiteCompra, diaFechamento);

        assertEquals(clienteEsperado, clienteRetornado, "O cliente retornado deve ser igual ao cliente esperado.");
    }

    @Test
    public void testDataFechamentoUltimoDiaFevereiro() throws Exception {
        Long clienteId = 1L;
        Cliente cliente = new Cliente(clienteId, "Cliente Teste", BigDecimal.valueOf(1000), 30);

        Mockito.when(clienteRepository.select(clienteId)).thenReturn(cliente);

        LocalDate hoje = LocalDate.of(2024, Month.MARCH, 15);

        LocalDate dataFechamento = hoje.minusMonths(1).withDayOfMonth(cliente.getDiaFechamento())
                .with(TemporalAdjusters.lastDayOfMonth());

        LocalDate dataFechamentoEsperada = LocalDate.of(2024, Month.FEBRUARY, 29);

        assertEquals(dataFechamentoEsperada, dataFechamento,
                "A data de fechamento esperada deve ser o último dia de fevereiro quando a data atual é março e o fechamento é no dia 30.");
    }

    @Test
    public void testEditarClienteDiaFechamentoValido() throws Exception {
        Long id = 1L;
        String nome = "Cliente Atualizado";
        BigDecimal limiteCompra = BigDecimal.valueOf(2000);
        Integer diaFechamento = 15;

        Cliente clienteAtualizado = new Cliente(id, nome, limiteCompra, diaFechamento);
        Mockito.when(clienteRepository.update(id, nome, limiteCompra, diaFechamento)).thenReturn(clienteAtualizado);

        Cliente clienteResultante = clienteService.editarCliente(id, nome, limiteCompra, diaFechamento);

        assertNotNull(clienteResultante, "O cliente resultante não deve ser nulo.");
        assertEquals(id, clienteResultante.getId(), "O ID do cliente deve ser igual ao esperado.");
        assertEquals(nome, clienteResultante.getNome(), "O nome do cliente deve ser igual ao esperado.");
        assertEquals(limiteCompra, clienteResultante.getLimiteCompra(), "O limite de compra do cliente deve ser igual ao esperado.");
        assertEquals(diaFechamento, clienteResultante.getDiaFechamento(), "O dia de fechamento do cliente deve ser igual ao esperado.");
    }

    @Test
    public void testEditarClienteDiaFechamentoInvalido() {
        Long id = 1L;
        String nome = "Cliente Invalido";
        BigDecimal limiteCompra = BigDecimal.valueOf(2000);
        Integer diaFechamento = 32;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clienteService.editarCliente(id, nome, limiteCompra, diaFechamento);
        });

        assertEquals("A data de fechamento deve ser entre 1 e 31!", exception.getMessage(), "A mensagem da exceção deve ser a esperada.");
    }

    @Test
    public void testExcluirClienteIdValido() {
        Long id = 1L;

        Mockito.when(clienteRepository.delete(id)).thenReturn(id);

        Long result = null;
        try {
            result = clienteService.excluirCliente(id);
        } catch (Exception ex) {
            Logger.getLogger(ClienteServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        verify(clienteRepository).delete(id);

        assertEquals(id, result);
    }

    @Test
    public void testExcluirClienteInvalido() {
        Long id = 1L;

        Mockito.when(clienteRepository.delete(id)).thenThrow(new RuntimeException("Falha na exclusão"));

        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            clienteService.excluirCliente(id);
        });

        assertEquals("Falha na exclusão", thrownException.getMessage());
    }

}

package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.Cliente;
import AppGerenciamentoVendas.Repository.ClienteRepository;
import AppGerenciamentoVendas.Repository.PedidoRepository;
import AppGerenciamentoVendas.View.Cadastro.CadastroClienteView;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Luiz
 */
public class ClienteService {

    ClienteRepository clienteRepository = new ClienteRepository();
    PedidoRepository pedidoRepository = new PedidoRepository();
    CadastroClienteView clienteView;
    private List<Cliente> listaCliente = new ArrayList<>();
    private Cliente clienteEditar;
    private TableModel model;

    public void setClienteRepository(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
}

    /**
     * Carrega a lista completa de clientes a partir do reposit�rio.
     *
     * @return Uma lista de objetos {@link Cliente} contendo todos os clientes
     * cadastrados.
     */
    public List<Cliente> carregarListaClientes() {
        return clienteRepository.select();
    }

    /**
     * Carrega os dados de c�digo e nome de todos os clientes do reposit�rio e
     * os organiza em um modelo de tabela (`TableModel`) para exibi��o como
     * `JTable`. Este m�todo recupera todos os clientes, extrai os campos de
     * c�digo e nome, e os insere em uma matriz de objetos que ser� utilizada
     * para criar um `DefaultTableModel`.
     *
     * @return Um `TableModel` contendo os dados de c�digo e nome de todos os
     * clientes, com as colunas "Codigo" e "Nome". As c�lulas da tabela n�o s�o
     * edit�veis.
     */
    public TableModel carregarTabelaCodigoENome() {
        listaCliente = clienteRepository.select();

        Object[][] clientes = new Object[listaCliente.size()][Cliente.class.getDeclaredFields().length];

        int i = 0;
        for (Cliente cliente : listaCliente) {
            clientes[i][0] = cliente.getId();
            clientes[i][1] = cliente.getNome();
            i++;
        }
        return new DefaultTableModel(clientes, new Object[]{"Codigo", "Nome"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    /**
     * Carrega as informa��es de um cliente espec�fico com base no ID fornecido.
     * Este m�todo consulta o reposit�rio de clientes para encontrar o cliente
     * com o ID correspondente e retorna o objeto `Cliente` correspondente.
     *
     * @param id O ID do cliente a ser carregado.
     * @return O objeto `Cliente` correspondente ao ID fornecido. Retorna `null`
     * se o cliente n�o for encontrado.
     */
    public Cliente carregaCliente(Long id) {
        return clienteEditar = clienteRepository.select(id);
    }

    /**
     * Carrega o pr�ximo ID dispon�vel para um novo cliente. Este m�todo
     * consulta o reposit�rio e retorna o pr�ximo ID sequencial. Se n�o houver
     * nenhum cliente registrado, ele retorna 1 como o primeiro ID dispon�vel.
     *
     * @return O pr�ximo ID dispon�vel para um novo cliente. Se n�o houver
     * clientes registrados, retorna 1L.
     */
    public Long carregarId() {
        Long proximoId = clienteRepository.selectId();
        if (proximoId == null) {
            return 1L;
        } else {
            return proximoId + 1;
        }
    }

    /**
     * Cadastra um novo cliente no sistema com as informa��es fornecidas. Valida
     * se o dia de fechamento est� no intervalo entre 1 e 31. Se for inv�lido,
     * uma exce��o � lan�ada.
     *
     * @param nome O nome do cliente a ser cadastrado.
     * @param limiteCompra O limite de compra atribu�do ao cliente.
     * @param diaFechamento O dia do m�s em que o fechamento ocorre (deve estar
     * entre 1 e 31).
     * @return O objeto `Cliente`.
     * @throws IllegalArgumentException Se o dia de fechamento estiver fora do
     * intervalo permitido (1-31).
     * @throws Exception Se ocorrer algum erro durante o processo de cadastro no
     * reposit�rio.
     */
    public Cliente cadastrarCliente(String nome, BigDecimal limiteCompra, Integer diaFechamento) throws Exception {

        if (diaFechamento < 1 || diaFechamento > 31) {
            throw new IllegalArgumentException("A data de fechamento deve ser entre 1 e 31!");
        }
        return new ClienteRepository().insert(nome, limiteCompra, diaFechamento);
    }

    /**
     * Edita as informa��es de um cliente existente no sistema com base no ID
     * fornecido. Valida se o dia de fechamento est� no intervalo entre 1 e 31.
     * Se for inv�lido, uma exce��o � lan�ada.
     *
     * @param id O ID do cliente a ser editado.
     * @param nome O nome atualizado do cliente.
     * @param limiteCompra O limite de compra atualizado do cliente.
     * @param diaFechamento O dia do m�s atualizado em que o fechamento ocorre
     * (deve estar entre 1 e 31).
     * @return O objeto `Cliente` atualizado.
     * @throws IllegalArgumentException Se o dia de fechamento estiver fora do
     * intervalo permitido (1-31).
     * @throws Exception Se ocorrer algum erro durante o processo de atualiza��o
     * no reposit�rio.
     */
    public Cliente editarCliente(Long id, String nome, BigDecimal limiteCompra, Integer diaFechamento) throws Exception {

        if (diaFechamento < 1 || diaFechamento > 31) {
            throw new IllegalArgumentException("A data de fechamento deve ser entre 1 e 31!");
        }
        return new ClienteRepository().update(id, nome, limiteCompra, diaFechamento);
    }

    /**
     * Exclui um cliente do sistema com base no ID fornecido.
     *
     * @param id O ID do cliente a ser exclu�do.
     * @return O ID do cliente exclu�do.
     */
    public Long excluirCliente(Long id) throws Exception{
        return new ClienteRepository().delete(id);
    }

    /**
     * Verifica o limite de compra dispon�vel para um cliente com base no valor
     * total dos pedidos realizados at� a data de fechamento do m�s atual.
     *
     * <p>
     * Este m�todo recupera as informa��es de um cliente espec�fico com base no
     * ID fornecido e verifica se o valor do pedido n�o excede o limite de
     * compra dispon�vel. A data de fechamento � ajustada para o �ltimo dia do
     * m�s anterior caso o dia especificado pelo cliente n�o exista no m�s atual
     * (por exemplo, dia 31 em fevereiro).</p>
     *
     * @param clienteId O ID do cliente que o limite de compra ser� verificado.
     * @param valorPedido O valor do pedido que ser� comparado ao limite
     * dispon�vel.
     * @return O limite de compra dispon�vel para o cliente, ap�s subtrair o
     * todos os pedidos feitos desde a data do ultimo fechamento.
     * @throws Exception Se o cliente n�o for encontrado pelo ID fornecido.
     */
    public BigDecimal verificarLimiteCompra(Long clienteId, BigDecimal valorPedido) throws Exception {
        Cliente cliente = clienteRepository.select(clienteId);
        if (cliente == null) {
            throw new Exception("Cliente n�o encontrado.");
        }

        LocalDate hoje = LocalDate.now();

        LocalDate dataFechamento;
        try {
            dataFechamento = hoje.withDayOfMonth(cliente.getDiaFechamento());
        } catch (Exception e) {
            dataFechamento = hoje.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
        }

        if (hoje.isBefore(dataFechamento)) {
            dataFechamento = dataFechamento.minusMonths(1);
        }

        BigDecimal totalPedidos = pedidoRepository.totalPedidoCliente(clienteId, dataFechamento);
        BigDecimal limiteDisponivel = cliente.getLimiteCompra().subtract(totalPedidos);

        return limiteDisponivel;
    }

}

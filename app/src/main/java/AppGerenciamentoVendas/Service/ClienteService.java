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
     * Carrega a lista completa de clientes a partir do repositório.
     *
     * @return Uma lista de objetos {@link Cliente} contendo todos os clientes
     * cadastrados.
     */
    public List<Cliente> carregarListaClientes() {
        return clienteRepository.select();
    }

    /**
     * Carrega os dados de código e nome de todos os clientes do repositório e
     * os organiza em um modelo de tabela (`TableModel`) para exibição como
     * `JTable`. Este método recupera todos os clientes, extrai os campos de
     * código e nome, e os insere em uma matriz de objetos que será utilizada
     * para criar um `DefaultTableModel`.
     *
     * @return Um `TableModel` contendo os dados de código e nome de todos os
     * clientes, com as colunas "Codigo" e "Nome". As células da tabela não são
     * editáveis.
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
     * Carrega as informações de um cliente específico com base no ID fornecido.
     * Este método consulta o repositório de clientes para encontrar o cliente
     * com o ID correspondente e retorna o objeto `Cliente` correspondente.
     *
     * @param id O ID do cliente a ser carregado.
     * @return O objeto `Cliente` correspondente ao ID fornecido. Retorna `null`
     * se o cliente não for encontrado.
     */
    public Cliente carregaCliente(Long id) {
        return clienteEditar = clienteRepository.select(id);
    }

    /**
     * Carrega o próximo ID disponível para um novo cliente. Este método
     * consulta o repositório e retorna o próximo ID sequencial. Se não houver
     * nenhum cliente registrado, ele retorna 1 como o primeiro ID disponível.
     *
     * @return O próximo ID disponível para um novo cliente. Se não houver
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
     * Cadastra um novo cliente no sistema com as informações fornecidas. Valida
     * se o dia de fechamento está no intervalo entre 1 e 31. Se for inválido,
     * uma exceção é lançada.
     *
     * @param nome O nome do cliente a ser cadastrado.
     * @param limiteCompra O limite de compra atribuído ao cliente.
     * @param diaFechamento O dia do mês em que o fechamento ocorre (deve estar
     * entre 1 e 31).
     * @return O objeto `Cliente`.
     * @throws IllegalArgumentException Se o dia de fechamento estiver fora do
     * intervalo permitido (1-31).
     * @throws Exception Se ocorrer algum erro durante o processo de cadastro no
     * repositório.
     */
    public Cliente cadastrarCliente(String nome, BigDecimal limiteCompra, Integer diaFechamento) throws Exception {

        if (diaFechamento < 1 || diaFechamento > 31) {
            throw new IllegalArgumentException("A data de fechamento deve ser entre 1 e 31!");
        }
        return new ClienteRepository().insert(nome, limiteCompra, diaFechamento);
    }

    /**
     * Edita as informações de um cliente existente no sistema com base no ID
     * fornecido. Valida se o dia de fechamento está no intervalo entre 1 e 31.
     * Se for inválido, uma exceção é lançada.
     *
     * @param id O ID do cliente a ser editado.
     * @param nome O nome atualizado do cliente.
     * @param limiteCompra O limite de compra atualizado do cliente.
     * @param diaFechamento O dia do mês atualizado em que o fechamento ocorre
     * (deve estar entre 1 e 31).
     * @return O objeto `Cliente` atualizado.
     * @throws IllegalArgumentException Se o dia de fechamento estiver fora do
     * intervalo permitido (1-31).
     * @throws Exception Se ocorrer algum erro durante o processo de atualização
     * no repositório.
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
     * @param id O ID do cliente a ser excluído.
     * @return O ID do cliente excluído.
     */
    public Long excluirCliente(Long id) throws Exception{
        return new ClienteRepository().delete(id);
    }

    /**
     * Verifica o limite de compra disponível para um cliente com base no valor
     * total dos pedidos realizados até a data de fechamento do mês atual.
     *
     * <p>
     * Este método recupera as informações de um cliente específico com base no
     * ID fornecido e verifica se o valor do pedido não excede o limite de
     * compra disponível. A data de fechamento é ajustada para o último dia do
     * mês anterior caso o dia especificado pelo cliente não exista no mês atual
     * (por exemplo, dia 31 em fevereiro).</p>
     *
     * @param clienteId O ID do cliente que o limite de compra será verificado.
     * @param valorPedido O valor do pedido que será comparado ao limite
     * disponível.
     * @return O limite de compra disponível para o cliente, após subtrair o
     * todos os pedidos feitos desde a data do ultimo fechamento.
     * @throws Exception Se o cliente não for encontrado pelo ID fornecido.
     */
    public BigDecimal verificarLimiteCompra(Long clienteId, BigDecimal valorPedido) throws Exception {
        Cliente cliente = clienteRepository.select(clienteId);
        if (cliente == null) {
            throw new Exception("Cliente não encontrado.");
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

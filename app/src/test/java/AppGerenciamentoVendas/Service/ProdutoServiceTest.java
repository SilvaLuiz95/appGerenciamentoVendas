package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.Produto;
import AppGerenciamentoVendas.Repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
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
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @BeforeEach
    public void setUp() {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCarregarListaProdutosValidos() {
        List<Produto> produtosEsperados = Arrays.asList(
                new Produto(1L, "Produto1", new BigDecimal(10.0)),
                new Produto(2L, "Produto2", new BigDecimal(20.0))
        );

        Mockito.when(produtoRepository.select()).thenReturn(produtosEsperados);

        List<Produto> produtosRetornados = produtoService.carregarListaProdutos();

        assertNotNull(produtosRetornados, "A lista de produtos não deve ser nula");
        assertEquals(produtosEsperados.size(), produtosRetornados.size(), "A lista de produtos deve ter o tamanho correto");
        assertTrue(produtosRetornados.containsAll(produtosEsperados), "A lista de produtos deve conter todos os produtos esperados");

        verify(produtoRepository).select();
    }

    @Test
    public void testCarregaProduto_Sucesso() {
        Long idProduto = 1L;
        Produto produtoEsperado = new Produto(idProduto, "Produto1", new BigDecimal(10.0));

        Mockito.when(produtoRepository.select(idProduto)).thenReturn(produtoEsperado);

        Produto produtoRetornado = produtoService.carregaProduto(idProduto);

        assertNotNull(produtoRetornado, "O produto retornado não deve ser nulo");
        assertEquals(produtoEsperado.getId(), produtoRetornado.getId(), "O ID do produto não está correto");
        assertEquals(produtoEsperado.getDescricao(), produtoRetornado.getDescricao(), "O nome do produto não está correto");
        assertEquals(produtoEsperado.getPreco(), produtoRetornado.getPreco(), "O preço do produto não está correto");

        verify(produtoRepository).select(idProduto);
    }

    @Test
    public void testCarregaProdutoNaoEncontrado() {
        Long idProduto = 1L;

        Mockito.when(produtoRepository.select(idProduto)).thenReturn(null);

        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            produtoService.carregaProduto(idProduto);
        }, "Esperava-se que uma exceção NoSuchElementException fosse lançada");

        assertEquals("Nenhum produto encontrado com o ID fornecido", thrown.getMessage(), "A mensagem da exceção não é a esperada");

        verify(produtoRepository).select(idProduto);
    }

    @Test
    public void testCarregarIdComIdNull() {
        Long proximoIdEsperado = 1L;

        Mockito.when(produtoRepository.selectId()).thenReturn(null);

        Long proximoIdRetornado = produtoService.carregarId();

        assertEquals(proximoIdEsperado, proximoIdRetornado, "O próximo ID retornado deve ser 1 quando não há IDs disponíveis");

        verify(produtoRepository).selectId();
    }

    @Test
    public void testCarregarIdExistente() {
        Long idAtual = 5L;
        Long proximoIdEsperado = 6L;

        Mockito.when(produtoRepository.selectId()).thenReturn(idAtual);

        Long proximoIdRetornado = produtoService.carregarId();

        assertEquals(proximoIdEsperado, proximoIdRetornado, "O próximo ID retornado está incorreto");

        verify(produtoRepository).selectId();
    }

    @Test
    public void testCadastrarProdutoValido() throws Exception {
        String descricao = "Produto Teste";
        BigDecimal preco = new BigDecimal("100.00");
        Produto produtoEsperado = new Produto(1L, descricao, preco);

        Mockito.when(produtoRepository.insert(descricao, preco)).thenReturn(produtoEsperado);

        Produto produtoRetornado = produtoService.cadastrarProduto(descricao, preco);

        assertEquals(produtoEsperado, produtoRetornado, "O produto retornado não é o esperado");

        verify(produtoRepository).insert(descricao, preco);
    }

    @Test
    public void testCadastrarProdutoInvalido() throws Exception {
        String descricao = "Produto Teste";
        BigDecimal preco = new BigDecimal("100.00");

        Mockito.when(produtoRepository.insert(descricao, preco)).thenThrow(new Exception("Erro ao cadastrar o produto"));

        Exception exception = assertThrows(Exception.class, () -> {
            produtoService.cadastrarProduto(descricao, preco);
        });

        assertEquals("Erro ao cadastrar o produto", exception.getMessage(), "A mensagem de exceção não é a esperada");

        verify(produtoRepository).insert(descricao, preco);
    }

    @Test
    public void testEditarProdutoValido() throws Exception {
        Long id = 1L;
        String novaDescricao = "Produto Atualizado";
        BigDecimal novoPreco = new BigDecimal("150.00");
        Produto produtoAtualizado = new Produto(id, novaDescricao, novoPreco);

        Mockito.when(produtoRepository.update(id, novaDescricao, novoPreco)).thenReturn(produtoAtualizado);

        Produto produtoRetornado = produtoService.editarProduto(id, novaDescricao, novoPreco);

        assertEquals(produtoAtualizado, produtoRetornado, "O produto retornado não é o esperado");

        verify(produtoRepository).update(id, novaDescricao, novoPreco);
    }

    @Test
    public void testEditarProdutoInvalido() throws Exception {
        Long id = 1L;
        String novaDescricao = "Produto Atualizado";
        BigDecimal novoPreco = new BigDecimal("150.00");

        Mockito.when(produtoRepository.update(id, novaDescricao, novoPreco)).thenThrow(new Exception("Erro ao editar o produto"));

        Exception exception = assertThrows(Exception.class, () -> {
            produtoService.editarProduto(id, novaDescricao, novoPreco);
        });

        assertEquals("Erro ao editar o produto", exception.getMessage(), "A mensagem de exceção não é a esperada");

        verify(produtoRepository).update(id, novaDescricao, novoPreco);
    }

    @Test
    public void testExcluirProdutoValido() throws Exception {
        Long id = 1L;

        Mockito.when(produtoRepository.delete(id)).thenReturn(id);

        Long idRetornado = produtoService.excluirProduto(id);

        assertEquals(id, idRetornado, "O ID retornado não é o esperado");

        verify(produtoRepository).delete(id);
    }

    @Test
    public void testExcluirProdutoInvalido() throws Exception {
        Long id = 1L;

        Mockito.when(produtoRepository.delete(id)).thenThrow(new Exception("Erro ao excluir o produto"));

        Exception exception = assertThrows(Exception.class, () -> {
            produtoService.excluirProduto(id);
        });

        assertEquals("Erro ao excluir o produto", exception.getMessage(), "A mensagem de exceção não é a esperada");

        verify(produtoRepository).delete(id);
    }
}

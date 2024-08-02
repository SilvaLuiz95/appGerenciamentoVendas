package AppGerenciamentoVendas.Service;

import AppGerenciamentoVendas.Model.Produto;
import AppGerenciamentoVendas.Repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Luiz
 */
public class ProdutoService {

    ProdutoRepository produtoRepository = new ProdutoRepository();
    private List<Produto> listaProduto = new ArrayList<>();
    private Produto produtoEditar;

    /**
     * Carrega a lista completa de produtos a partir do reposit�rio.
     *
     * @return Uma lista de objetos {@link Produto} contendo todos os produtos
     * cadastrados.
     */
    public List<Produto> carregarListaProdutos() {
        return produtoRepository.select();
    }

    /**
     * Carrega um produto espec�fico com base no seu ID.
     *
     * @param id O ID do produto a ser carregado.
     * @return Um objeto {@link Produto} correspondente ao ID fornecido.
     * @throws NoSuchElementException Se nenhum produto com o ID fornecido for
     * encontrado.
     */
    public Produto carregaProduto(Long id) {
        return produtoEditar = produtoRepository.select(id);
    }

    /**
     * Retorna o pr�ximo ID dispon�vel para um novo produto. O pr�ximo ID �
     * calculado com base no maior ID existente no reposit�rio.
     *
     * @return O pr�ximo ID dispon�vel para um novo produto.
     */
    public Long carregarId() {
        Long proximoId = produtoRepository.selectId();
        if (proximoId == null) {
            return 1L;
        } else {
            return proximoId + 1;
        }
    }

    /**
     * Cadastra um novo produto no reposit�rio com a descri��o e pre�o
     * fornecidos.
     *
     * @param descricao A descri��o do novo produto.
     * @param preco O pre�o do novo produto.
     * @return Um objeto {@link Produto} representando o produto
     * rec�m-cadastrado.
     * @throws Exception Se ocorrer um erro ao tentar cadastrar o produto.
     */
    public Produto cadastrarProduto(String descricao, BigDecimal preco) throws Exception {

        return new ProdutoRepository().insert(descricao, preco);
    }

    /**
     * Edita um produto existente com base no ID fornecido, atualizando sua
     * descri��o e pre�o.
     *
     * @param id O ID do produto a ser editado.
     * @param descricao A nova descri��o para o produto.
     * @param preco O novo pre�o para o produto.
     * @return Um objeto {@link Produto} representando o produto atualizado.
     * @throws Exception Se ocorrer um erro ao tentar editar o produto.
     */
    public Produto editarProduto(Long id, String descricao, BigDecimal preco) throws Exception {

        return new ProdutoRepository().update(id, descricao, preco);
    }

    /**
     * Exclui um produto do reposit�rio com base no ID fornecido.
     *
     * @param id O ID do produto a ser exclu�do.
     * @return O ID do produto que foi exclu�do.
     */
    public Long excluirProduto(Long id)throws Exception{
        return new ProdutoRepository().delete(id);
    }

}

package repository;

import datamanager.DataManager;
import domain.Livro;
import entities.EntidadeLivro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repositório responsável pela persistência e recuperação de objetos da classe {@link Livro}.
 * Gerencia a comunicação com o {@link DataManager} para manter a consistência dos dados
 * em arquivos JSON e fornece métodos para busca e manipulação do acervo.
 */
public class RepositorioLivros {

    /**
     * Mapa em memória que armazena as entidades de livros, indexadas pelo identificador.
     */
    private final Map<Integer, EntidadeLivro> livroMap = new HashMap<>();
    private final DataManager dataManager;
    private static final String LIVROS_FILENAME = "livros";

    /**
     * Constrói o repositório e inicializa o cache em memória com os dados recuperados via {@link DataManager}.
     *
     * @param dataManager instância do gerenciador de dados para persistência.
     */
    public RepositorioLivros(DataManager dataManager) {
        this.dataManager = dataManager;

        List<EntidadeLivro> entidadeLivroList = dataManager.buscar(LIVROS_FILENAME, EntidadeLivro.class);
        for (EntidadeLivro entidadeLivro : entidadeLivroList) {
            livroMap.put(entidadeLivro.getId(), entidadeLivro);
        }
    }

    /**
     * Persiste o estado atual de um livro no armazenamento de dados.
     *
     * @param livro o objeto de domínio {@link Livro} a ser salvo.
     */
    public void salvar(Livro livro) {
        livroMap.put(livro.getId(), EntidadeLivro.converterParaEntidade(livro));
        dataManager.salvar(LIVROS_FILENAME, livroMap.values());
    }

    /**
     * Busca um livro no repositório através de seu identificador único.
     *
     * @param id identificador do livro.
     * @return um {@link Optional} contendo o livro caso encontrado, ou vazio caso contrário.
     */
    public Optional<Livro> buscar(int id) {
        return Optional.ofNullable(livroMap.get(id)).map(EntidadeLivro::converterParaLivro);
    }

    /**
     * Realiza uma busca textual no acervo pelos campos título, autor ou categoria.
     * Considera apenas livros com disponibilidade no estoque (quantidade total > 0).
     *
     * @param termoPesquisa o termo utilizado para filtrar os resultados (ignora maiúsculas/minúsculas).
     * @return uma lista de livros que correspondem ao critério de pesquisa.
     */
    public List<Livro> buscar(String termoPesquisa) {
        return livroMap.values().stream()
                .filter(entidadeLivro -> entidadeLivro.getQuantidadeTotal() > 0)
                .filter(entidadeLivro -> entidadeLivro.getTitulo().toLowerCase().contains(termoPesquisa.toLowerCase())
                    || entidadeLivro.getAutor().toLowerCase().contains(termoPesquisa.toLowerCase())
                    || entidadeLivro.getCategoria().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeLivro::converterParaLivro)
                .toList();
    }

    /**
     * Calcula o próximo identificador incremental disponível para um novo registro de livro.
     *
     * @return o próximo ID a ser utilizado.
     */
    public int getProximoId() {
        return livroMap.keySet().stream().mapToInt(o -> o).max().orElse(0) + 1;
    }
}
package repository;

import datamanager.DataManager;
import domain.Livro;
import entities.EntidadeLivro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório responsável pelo armazenamento e recuperação
 * dos livros cadastrados na biblioteca.
 */
public class RepositorioLivros {

    private final Map<Integer, EntidadeLivro> livroMap = new HashMap<>();
    private final DataManager dataManager;

    private static final String LIVROS_FILENAME = "livros";

    /**
     * Inicializa o repositório carregando os livros salvos.
     *
     * @param dataManager gerenciador de persistência.
     */
    public RepositorioLivros(DataManager dataManager) {
        this.dataManager = dataManager;

        List<EntidadeLivro> entidadeLivroList =
                dataManager.buscar(LIVROS_FILENAME, EntidadeLivro.class);

        for (EntidadeLivro entidadeLivro : entidadeLivroList) {
            livroMap.put(entidadeLivro.getId(), entidadeLivro);
        }
    }

    /**
     * Salva um livro.
     *
     * @param livro livro a ser salvo.
     */
    public void salvar(Livro livro) {
        livroMap.put(livro.getId(), EntidadeLivro.converterParaEntidade(livro));

        dataManager.salvar(LIVROS_FILENAME, livroMap.values());
    }

    /**
     * Verifica se existe um livro com o id informado.
     *
     * @param id identificador do livro.
     * @return true se o livro existir.
     */
    public boolean contemId(int id) {
        return livroMap.containsKey(id);
    }

    /**
     * Busca um livro pelo id.
     *
     * @param id identificador do livro.
     * @return livro encontrado.
     */
    public Livro buscar(int id) {
        return livroMap.get(id).converterParaLivro();
    }

    /**
     * Busca livros por título, autor ou categoria.
     *
     * @param termoPesquisa termo utilizado na pesquisa.
     * @return lista de livros encontrados.
     */
    public List<Livro> buscar(String termoPesquisa) {
        return livroMap.values().stream()
                .filter(entidadeLivro -> entidadeLivro.getQuantidadeTotal() > 0)
                .filter(entidadeLivro ->
                        entidadeLivro.getTitulo().toLowerCase().contains(termoPesquisa.toLowerCase())
                                || entidadeLivro.getAutor().toLowerCase().contains(termoPesquisa.toLowerCase())
                                || entidadeLivro.getCategoria().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeLivro::converterParaLivro)
                .toList();
    }

    /**
     * Obtém o próximo id disponível para cadastro.
     *
     * @return próximo identificador.
     */
    public int getProximoId() {
        return livroMap.keySet()
                .stream()
                .mapToInt(o -> o)
                .max()
                .orElse(0) + 1;
    }
}

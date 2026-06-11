package services;

import domain.Livro;
import repository.RepositorioLivros;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pelas operações relacionadas aos livros
 * da biblioteca.
 */
public class ServicoLivros {

    private final RepositorioLivros repositorioLivros;

    /**
     * Cria uma instância do serviço de livros.
     *
     * @param repositorioLivros repositório de livros.
     */
    public ServicoLivros(RepositorioLivros repositorioLivros) {
        this.repositorioLivros = repositorioLivros;
    }

    /**
     * Busca livros pelo título, autor ou categoria.
     *
     * @param termoPesquisa termo utilizado na pesquisa.
     * @return lista de livros encontrados.
     */
    public List<Livro> buscar(String termoPesquisa) {
        return repositorioLivros.buscar(termoPesquisa);
    }

    /**
     * Busca um livro pelo identificador.
     *
     * @param id identificador do livro.
     * @return livro encontrado, caso exista.
     */
    public Optional<Livro> buscarPorId(int id) {
        if (repositorioLivros.contemId(id)) {
            return Optional.of(repositorioLivros.buscar(id));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Cadastra um novo livro no sistema.
     *
     * @param titulo título do livro.
     * @param autor autor do livro.
     * @param categoria categoria do livro.
     * @param quantidadeTotal quantidade total disponível.
     */
    public void cadastrarLivro(String titulo, String autor,
                               String categoria, int quantidadeTotal) {

        int id = repositorioLivros.getProximoId();
        Livro livro = new Livro(id, titulo, autor, categoria, quantidadeTotal);

        repositorioLivros.salvar(livro);
    }

    /**
     * Altera a quantidade total de exemplares de um livro.
     *
     * @param id identificador do livro.
     * @param quantidadeTotal nova quantidade total.
     */
    public void alteraQuantidadeTotal(int id, int quantidadeTotal) {
        if (!repositorioLivros.contemId(id)) return;

        Livro livro = repositorioLivros.buscar(id);

        if (quantidadeTotal < livro.getQuantidadeEmprestada()) return;

        livro.setQuantidadeTotal(quantidadeTotal);

        repositorioLivros.salvar(livro);
    }

    /**
     * Atualiza os dados de um livro cadastrado.
     *
     * @param id identificador do livro.
     * @param titulo novo título.
     * @param autor novo autor.
     * @param categoria nova categoria.
     */
    public void editarLivro(int id, String titulo,
                            String autor, String categoria) {

        if (!repositorioLivros.contemId(id)) return;

        Livro livro = repositorioLivros.buscar(id);

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        repositorioLivros.salvar(livro);
    }
}

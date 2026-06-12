package services;

import domain.Livro;
import repository.RepositorioLivros;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela gestão das regras de negócio relacionadas aos livros.
 * Coordena as operações de cadastro, edição e busca no acervo, interagindo
 * com o {@link RepositorioLivros} para persistência.
 */
public class ServicoLivros {

    private final RepositorioLivros repositorioLivros;

    /**
     * Constrói o serviço com a instância do repositório de livros necessária.
     *
     * @param repositorioLivros repositório de livros a ser utilizado.
     */
    public ServicoLivros(RepositorioLivros repositorioLivros) {
        this.repositorioLivros = repositorioLivros;
    }

    /**
     * Busca livros no acervo baseada em um termo de pesquisa.
     *
     * @param termoPesquisa o termo para filtragem.
     * @return lista de livros encontrados.
     */
    public List<Livro> buscar(String termoPesquisa) {
        return repositorioLivros.buscar(termoPesquisa);
    }

    /**
     * Busca um livro específico pelo seu identificador.
     *
     * @param id identificador do livro.
     * @return um {@link Optional} contendo o livro, se encontrado.
     */
    public Optional<Livro> buscarPorId(int id) {
        return repositorioLivros.buscar(id);
    }

    /**
     * Realiza o cadastro de um novo livro no sistema, atribuindo um novo identificador.
     *
     * @param titulo          título da obra.
     * @param autor           autor do livro.
     * @param categoria       categoria ou gênero literário.
     * @param quantidadeTotal quantidade total de exemplares.
     */
    public void cadastrarLivro(String titulo, String autor, String categoria, int quantidadeTotal) {
        int id = repositorioLivros.getProximoId();
        Livro livro = new Livro(id, titulo, autor, categoria, quantidadeTotal);

        repositorioLivros.salvar(livro);
    }

    /**
     * Altera a quantidade total de exemplares de um livro, validando se a nova quantidade
     * é compatível com o número de exemplares atualmente emprestados.
     *
     * @param id              identificador do livro.
     * @param quantidadeTotal nova quantidade total.
     */
    public void alteraQuantidadeTotal(int id, int quantidadeTotal) {
        Optional<Livro> livroOpt = repositorioLivros.buscar(id);

        if (livroOpt.isEmpty()) return;

        Livro livro = livroOpt.get();

        if(quantidadeTotal < livro.getQuantidadeEmprestada()) return;

        livro.setQuantidadeTotal(quantidadeTotal);

        repositorioLivros.salvar(livro);
    }

    /**
     * Edita as informações bibliográficas de um livro existente.
     *
     * @param id        identificador do livro.
     * @param titulo    novo título.
     * @param autor     novo autor.
     * @param categoria nova categoria.
     */
    public void editarLivro(int id, String titulo, String autor, String categoria) {
        Optional<Livro> livroOpt = repositorioLivros.buscar(id);

        if (livroOpt.isEmpty()) return;

        Livro livro = livroOpt.get();

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        repositorioLivros.salvar(livro);
    }
}
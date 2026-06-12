package entities;

import domain.Livro;

/**
 * Representa a entidade de persistência para objetos do tipo {@link Livro}.
 * Atua como intermediária entre a estrutura de armazenamento de dados e 
 * o modelo de domínio, permitindo a conversão dos dados do livro.
 */
public class EntidadeLivro {
    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private int quantidadeTotal;
    private int quantidadeEmprestada;

    /**
     * Construtor padrão necessário para frameworks de persistência.
     */
    public EntidadeLivro() {}

    /**
     * Construtor privado para inicialização dos dados da entidade.
     *
     * @param id                   identificador único do livro.
     * @param titulo               título da obra.
     * @param autor                autor do livro.
     * @param categoria            categoria ou gênero literário.
     * @param quantidadeTotal      quantidade total de exemplares.
     * @param quantidadeEmprestada quantidade de exemplares emprestados.
     */
    private EntidadeLivro(int id, String titulo, String autor, String categoria, int quantidadeTotal, int quantidadeEmprestada) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeEmprestada = quantidadeEmprestada;
    }

    /**
     * @return o identificador do livro.
     */
    public int getId() {
        return id;
    }

    /**
     * @return o título do livro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return o nome do autor do livro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @return a categoria do livro.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @return a quantidade total de exemplares.
     */
    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    /**
     * @return a quantidade de exemplares emprestados.
     */
    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    /**
     * Converte esta entidade em um objeto de domínio {@link Livro}.
     *
     * @return uma instância de {@link Livro} populada com os dados desta entidade.
     */
    public Livro converterParaLivro() {
        return new Livro(id, titulo, autor, categoria, quantidadeTotal, quantidadeEmprestada);
    }

    /**
     * Converte um objeto de domínio {@link Livro} em uma entidade de persistência {@link EntidadeLivro}.
     *
     * @param livro o objeto de domínio a ser convertido.
     * @return a entidade correspondente.
     */
    public static EntidadeLivro converterParaEntidade(Livro livro) {
        return new EntidadeLivro(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getCategoria(),
                livro.getQuantidadeTotal(),
                livro.getQuantidadeEmprestada());
    }
}
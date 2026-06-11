package entities;

import domain.Livro;

/**
 * Representa a entidade de persistência de um livro.
 *
 * Utilizada para armazenar e recuperar dados de livros
 * nos arquivos da aplicação.
 */
public class EntidadeLivro {

    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private int quantidadeTotal;
    private int quantidadeEmprestada;

    /**
     * Construtor vazio utilizado na desserialização.
     */
    public EntidadeLivro() {}

    /**
     * Cria uma entidade de livro.
     *
     * @param id identificador do livro.
     * @param titulo título do livro.
     * @param autor autor do livro.
     * @param categoria categoria do livro.
     * @param quantidadeTotal quantidade total de exemplares.
     * @param quantidadeEmprestada quantidade de exemplares emprestados.
     */
    private EntidadeLivro(int id, String titulo, String autor,
                          String categoria, int quantidadeTotal,
                          int quantidadeEmprestada) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeEmprestada = quantidadeEmprestada;
    }

    /**
     * @return identificador do livro.
     */
    public int getId() {
        return id;
    }

    /**
     * @return título do livro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @return autor do livro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @return categoria do livro.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @return quantidade total de exemplares.
     */
    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    /**
     * @return quantidade de exemplares emprestados.
     */
    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    /**
     * Converte a entidade para um objeto Livro.
     *
     * @return objeto do tipo Livro.
     */
    public Livro converterParaLivro() {
        return new Livro(
                id,
                titulo,
                autor,
                categoria,
                quantidadeTotal,
                quantidadeEmprestada
        );
    }

    /**
     * Converte um livro para sua entidade de persistência.
     *
     * @param livro livro a ser convertido.
     * @return entidade correspondente.
     */
    public static EntidadeLivro converterParaEntidade(Livro livro) {
        return new EntidadeLivro(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getCategoria(),
                livro.getQuantidadeTotal(),
                livro.getQuantidadeEmprestada()
        );
    }
}package entities;

import domain.Livro;

public class EntidadeLivro {
    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private int quantidadeTotal;
    private int quantidadeEmprestada;

    public EntidadeLivro() {}

    private EntidadeLivro(int id, String titulo, String autor, String categoria, int quantidadeTotal, int quantidadeEmprestada) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeEmprestada = quantidadeEmprestada;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    public Livro converterParaLivro() {
        return new Livro(id, titulo, autor, categoria, quantidadeTotal, quantidadeEmprestada);
    }

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

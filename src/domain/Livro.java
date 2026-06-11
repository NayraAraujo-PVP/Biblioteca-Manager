package domain;

/**
 * Representa um livro cadastrado na biblioteca.
 *
 * Armazena informações sobre o livro e a quantidade
 * de exemplares disponíveis para empréstimo.
 */
public class Livro {

    private final int id;
    private String titulo;
    private String autor;
    private String categoria;
    private int quantidadeTotal;
    private int quantidadeEmprestada;

    /**
     * Cria um novo livro.
     *
     * @param id identificador do livro.
     * @param titulo título do livro.
     * @param autor autor do livro.
     * @param categoria categoria do livro.
     * @param quantidadeTotal quantidade total de exemplares.
     */
    public Livro(int id, String titulo, String autor,
                 String categoria, int quantidadeTotal) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.quantidadeTotal = quantidadeTotal;
        quantidadeEmprestada = 0;
    }

    /**
     * Cria um livro com todos os dados preenchidos.
     *
     * @param id identificador do livro.
     * @param titulo título do livro.
     * @param autor autor do livro.
     * @param categoria categoria do livro.
     * @param quantidadeTotal quantidade total de exemplares.
     * @param quantidadeEmprestada quantidade de exemplares emprestados.
     */
    public Livro(int id, String titulo, String autor,
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
     * Define o título do livro.
     *
     * @param titulo novo título.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return autor do livro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Define o autor do livro.
     *
     * @param autor novo autor.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return categoria do livro.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do livro.
     *
     * @param categoria nova categoria.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return quantidade total de exemplares.
     */
    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    /**
     * Define a quantidade total de exemplares.
     *
     * @param quantidadeTotal nova quantidade total.
     */
    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    /**
     * @return quantidade de exemplares emprestados.
     */
    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    /**
     * Verifica se há exemplares disponíveis para empréstimo.
     *
     * @return true se existir ao menos um exemplar disponível.
     */
    public boolean verificaDisponivel() {
        return quantidadeEmprestada < quantidadeTotal;
    }

    /**
     * Incrementa a quantidade de livros emprestados.
     */
    public void adicionarUmEmprestado() {
        if (verificaDisponivel()) {
            quantidadeEmprestada++;
        }
    }

    /**
     * Decrementa a quantidade de livros emprestados.
     */
    public void retirarUmEmprestado() {
        if (quantidadeEmprestada > 0) {
            quantidadeEmprestada--;
        }
    }
}

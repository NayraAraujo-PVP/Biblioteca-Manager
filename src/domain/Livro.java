package domain;

/**
 * Representa um livro no sistema da biblioteca.
 * Gerencia informações bibliográficas, bem como o controle de estoque 
 * e a disponibilidade de exemplares para empréstimo.
 */
public class Livro {
    private final int id;
    private String titulo;
    private String autor;
    private String categoria;
    private int quantidadeTotal;
    private int quantidadeEmprestada;

    /**
     * Construtor para inicialização de um novo livro no sistema.
     * A quantidade emprestada é inicializada como zero.
     *
     * @param id              identificador único do livro.
     * @param titulo          título da obra.
     * @param autor           autor do livro.
     * @param categoria       categoria ou gênero literário.
     * @param quantidadeTotal quantidade total de exemplares disponíveis na biblioteca.
     */
    public Livro(int id, String titulo, String autor, String categoria, int quantidadeTotal) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.quantidadeTotal = quantidadeTotal;
        quantidadeEmprestada = 0;
    }

    /**
     * Construtor completo para inicialização de um livro com histórico de empréstimos.
     *
     * @param id                   identificador único do livro.
     * @param titulo               título da obra.
     * @param autor                autor do livro.
     * @param categoria            categoria ou gênero literário.
     * @param quantidadeTotal      quantidade total de exemplares.
     * @param quantidadeEmprestada quantidade de exemplares atualmente emprestados.
     */
    public Livro(int id, String titulo, String autor, String categoria, int quantidadeTotal, int quantidadeEmprestada) {
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
     * @param titulo o novo título a ser definido.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return o nome do autor do livro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * @param autor o autor a ser definido.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * @return a categoria do livro.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * @param categoria a categoria a ser definida.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * @return a quantidade total de exemplares.
     */
    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    /**
     * @param quantidadeTotal a quantidade total a ser atualizada.
     */
    public void setQuantidadeTotal(int quantidadeTotal) {
        this.quantidadeTotal = quantidadeTotal;
    }

    /**
     * @return a quantidade de exemplares emprestados.
     */
    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
    }

    /**
     * Verifica se há exemplares disponíveis para empréstimo.
     *
     * @return true se houver exemplares disponíveis, false caso contrário.
     */
    public boolean verificaDisponivel() {
        return quantidadeEmprestada < quantidadeTotal;
    }

    /**
     * Incrementa o contador de exemplares emprestados, se houver disponibilidade.
     */
    public void adicionarUmEmprestado() {
        if (verificaDisponivel()) {
            quantidadeEmprestada++;
        }
    }

    /**
     * Decrementa o contador de exemplares emprestados, se houver exemplares registrados como emprestados.
     */
    public void retirarUmEmprestado() {
        if (quantidadeEmprestada > 0) {
            quantidadeEmprestada--;
        }
    }
}
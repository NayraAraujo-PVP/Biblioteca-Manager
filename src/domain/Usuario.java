package domain;

/**
 * Representa um usuário da biblioteca.
 *
 * Esta classe serve como base para alunos e docentes,
 * definindo informações e comportamentos comuns.
 */
public abstract class Usuario {

    private String nome;
    private final String cpf;
    private int emprestimosAtivos;

    /**
     * Cria um novo usuário.
     *
     * @param cpf CPF do usuário.
     * @param nome nome do usuário.
     */
    protected Usuario(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        emprestimosAtivos = 0;
    }

    /**
     * Cria um usuário com empréstimos já registrados.
     *
     * @param nome nome do usuário.
     * @param cpf CPF do usuário.
     * @param emprestimosAtivos quantidade de empréstimos ativos.
     */
    public Usuario(String nome, String cpf, int emprestimosAtivos) {
        this.nome = nome;
        this.cpf = cpf;
        this.emprestimosAtivos = emprestimosAtivos;
    }

    /**
     * @return nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     *
     * @param nome novo nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return CPF do usuário.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return quantidade de empréstimos ativos.
     */
    public int getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    /**
     * Adiciona um empréstimo ao usuário.
     */
    public void addEmprestimo() {
        if (verificaLimiteEmprestimos()) {
            emprestimosAtivos++;
        }
    }

    /**
     * Verifica se o usuário ainda pode realizar empréstimos.
     *
     * @return true se estiver dentro do limite permitido.
     */
    public abstract boolean verificaLimiteEmprestimos();

    /**
     * Remove um empréstimo ativo do usuário.
     */
    public void removeEmprestimo() {
        if (emprestimosAtivos > 0) {
            emprestimosAtivos--;
        }
    }

    /**
     * Retorna o prazo de permanência de um livro.
     *
     * @return prazo em dias.
     */
    public abstract int prazoPermanencia();

    /**
     * Calcula os dias de atraso na devolução.
     *
     * @param diasPermanenciaLivro quantidade de dias que o livro permaneceu emprestado.
     * @return quantidade de dias de atraso.
     */
    public abstract int calculaDiasAtraso(int diasPermanenciaLivro);

    /**
     * Calcula o valor da multa por atraso.
     *
     * @param diasDeAtraso quantidade de dias em atraso.
     * @return valor da multa.
     */
    public abstract Double calculaMulta(int diasDeAtraso);
}

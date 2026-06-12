package domain;

/**
 * Representa a abstração de um usuário no sistema de biblioteca.
 * Define os atributos e comportamentos comuns para diferentes tipos de usuários,
 * além de declarar métodos obrigatórios para regras de negócio específicas
 * de cada subtipo.
 */
public abstract class Usuario {
    
    private String nome;
    private final String cpf;
    private int emprestimosAtivos;

    /**
     * Construtor para inicialização básica de um novo usuário.
     * O contador de empréstimos ativos é inicializado como zero.
     *
     * @param cpf  o CPF do usuário (identificador único).
     * @param nome o nome completo do usuário.
     */
    protected Usuario(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        emprestimosAtivos = 0;
    }

    /**
     * Construtor para inicialização de um usuário com histórico de empréstimos.
     *
     * @param nome              o nome completo do usuário.
     * @param cpf               o CPF do usuário.
     * @param emprestimosAtivos a quantidade de empréstimos atualmente ativos.
     */
    public Usuario(String nome, String cpf, int emprestimosAtivos) {
        this.nome = nome;
        this.cpf = cpf;
        this.emprestimosAtivos = emprestimosAtivos;
    }

    /**
     * @return o nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome o novo nome a ser definido.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return o CPF do usuário.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return a quantidade de empréstimos ativos do usuário.
     */
    public int getEmprestimosAtivos() {
        return emprestimosAtivos;
    }
}

    /**
     * Incrementa o contador de empréstimos ativos, caso o usuário não tenha atingido o limite permitido.*/
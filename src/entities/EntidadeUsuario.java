package entities;

import domain.Usuario;

/**
 * Representa a entidade base de persistência para usuários.
 *
 * Armazena os dados comuns entre alunos e docentes.
 */
public abstract class EntidadeUsuario {

    protected String nome;
    protected String cpf;
    protected int emprestimosAtivos;

    /**
     * Construtor vazio utilizado na desserialização.
     */
    protected EntidadeUsuario() {}

    /**
     * Cria uma entidade de usuário.
     *
     * @param nome nome do usuário.
     * @param cpf CPF do usuário.
     * @param emprestimosAtivos quantidade de empréstimos ativos.
     */
    protected EntidadeUsuario(String nome, String cpf, int emprestimosAtivos) {
        this.nome = nome;
        this.cpf = cpf;
        this.emprestimosAtivos = emprestimosAtivos;
    }

    /**
     * @return CPF do usuário.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return quantidade de empréstimos ativos.
     */
    public int getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    /**
     * Converte a entidade para um objeto do domínio.
     *
     * @return usuário correspondente.
     */
    public abstract Usuario converterParaUsuario();
}

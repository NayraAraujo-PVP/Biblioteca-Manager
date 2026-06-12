package entities;

import domain.Usuario;

/**
 * Representa a abstração da entidade de persistência para objetos do tipo {@link Usuario}.
 * Define a estrutura comum para as entidades de usuários e estabelece o contrato 
 * para a conversão polimórfica para o modelo de domínio.
 */
public abstract class EntidadeUsuario {
    
    protected String nome;
    protected String cpf;
    protected int emprestimosAtivos;

    /**
     * Construtor padrão protegido, utilizado por subclasses para frameworks de persistência.
     */
    protected EntidadeUsuario() {}

    /**
     * Construtor protegido para inicialização dos dados comuns da entidade.
     *
     * @param nome              o nome do usuário.
     * @param cpf               o CPF do usuário.
     * @param emprestimosAtivos a quantidade de empréstimos ativos.
     */
    protected EntidadeUsuario(String nome, String cpf, int emprestimosAtivos) {
        this.nome = nome;
        this.cpf = cpf;
        this.emprestimosAtivos = emprestimosAtivos;
    }

    /**
     * @return o CPF do usuário.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * @return o nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return a quantidade de empréstimos ativos registrados na persistência.
     */
    public int getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    /**
     * Realiza a conversão polimórfica desta entidade para o respectivo objeto de domínio {@link Usuario}.
     *
     * @return uma instância de {@link Usuario} correspondente ao subtipo da entidade.
     */
    public abstract Usuario converterParaUsuario();
}
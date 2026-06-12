package entities;

import domain.Aluno;
import domain.Usuario;

/**
 * Representa a entidade de persistência para objetos do tipo {@link Aluno}.
 * Atua como uma camada de tradução entre o modelo de domínio e a representação 
 * de dados, facilitando a conversão bidirecional.
 */
public class EntidadeAluno extends EntidadeUsuario {

    /**
     * Matrícula do aluno, utilizada para fins de armazenamento.
     */
    private String matricula;

    /**
     * Construtor padrão necessário para frameworks de persistência.
     */
    public EntidadeAluno() {}

    /**
     * Construtor para inicialização dos dados da entidade.
     *
     * @param nome              o nome do aluno.
     * @param cpf               o CPF do aluno.
     * @param emprestimosAtivos a quantidade de empréstimos ativos.
     * @param matricula         a matrícula do aluno.
     */
    protected EntidadeAluno(String nome, String cpf, int emprestimosAtivos, String matricula) {
        super(nome, cpf, emprestimosAtivos);
        this.matricula = matricula;
    }

    /**
     * @return a matrícula do aluno.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Converte esta entidade em um objeto de domínio {@link Aluno}.
     *
     * @return uma instância de {@link Aluno} populada com os dados desta entidade.
     */
    public Aluno converterParaAluno() {
        return new Aluno(nome, cpf, emprestimosAtivos, matricula);
    }

    /**
     * Realiza a conversão polimórfica desta entidade para um objeto de domínio do tipo {@link Usuario}.
     *
     * @return uma instância de {@link Usuario} baseada no aluno.
     */
    @Override
    public Usuario converterParaUsuario() {
        return converterParaAluno();
    }

    /**
     * Converte um objeto de domínio {@link Aluno} em uma entidade de persistência {@link EntidadeAluno}.
     *
     * @param aluno o objeto de domínio a ser convertido.
     * @return a entidade correspondente.
     */
    public static EntidadeAluno converterParaEntidade(Aluno aluno) {
        return new EntidadeAluno(
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getEmprestimosAtivos(),
                aluno.getMatricula());
    }
}
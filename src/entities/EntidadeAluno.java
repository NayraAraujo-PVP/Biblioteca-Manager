package entities;

import domain.Aluno;
import domain.Usuario;

/**
 * Representa a entidade de persistência de um aluno.
 *
 * Utilizada para armazenar e recuperar dados de alunos
 * nos arquivos da aplicação.
 */
public class EntidadeAluno extends EntidadeUsuario {

    private String matricula;

    /**
     * Construtor vazio utilizado na desserialização.
     */
    public EntidadeAluno() {}

    /**
     * Cria uma entidade de aluno.
     *
     * @param nome nome do aluno.
     * @param cpf CPF do aluno.
     * @param emprestimosAtivos quantidade de empréstimos ativos.
     * @param matricula matrícula do aluno.
     */
    protected EntidadeAluno(String nome, String cpf,
                            int emprestimosAtivos,
                            String matricula) {
        super(nome, cpf, emprestimosAtivos);
        this.matricula = matricula;
    }

    /**
     * @return matrícula do aluno.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Converte a entidade para um objeto Aluno.
     *
     * @return objeto do tipo Aluno.
     */
    public Aluno converterParaAluno() {
        return new Aluno(nome, cpf, emprestimosAtivos, matricula);
    }

    /**
     * Converte a entidade para um usuário.
     *
     * @return usuário correspondente.
     */
    @Override
    public Usuario converterParaUsuario() {
        return converterParaAluno();
    }

    /**
     * Converte um aluno para sua entidade de persistência.
     *
     * @param aluno aluno a ser convertido.
     * @return entidade correspondente.
     */
    public static EntidadeAluno converterParaEntidade(Aluno aluno) {
        return new EntidadeAluno(
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getEmprestimosAtivos(),
                aluno.getMatricula()
        );
    }
}
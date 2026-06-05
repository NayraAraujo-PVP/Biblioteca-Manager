package entities;

import domain.Aluno;
import domain.Usuario;

public class EntidadeAluno extends EntidadeUsuario {
    private final String matricula;

    protected EntidadeAluno(String nome, String cpf, int emprestimosAtivos, String matricula) {
        super(nome, cpf, emprestimosAtivos);
        this.matricula = matricula;
    }

    public Aluno converterParaAluno() {
        return new Aluno(nome, cpf, emprestimosAtivos, matricula);
    }

    @Override
    public Usuario converterParaUsuario() {
        return converterParaAluno();
    }

    public static EntidadeAluno converterParaEntidade(Aluno aluno) {
        return new EntidadeAluno(
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getEmprestimosAtivos(),
                aluno.getMatricula());
    }
}

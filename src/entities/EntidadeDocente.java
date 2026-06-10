package entities;

import domain.Docente;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;

public class EntidadeDocente extends EntidadeUsuario {
    private String departamento;
    private TitulacaoAcademica titulacaoAcademica;

    public EntidadeDocente() {}

    protected EntidadeDocente(String nome, String cpf, int emprestimosAtivos, String departamento, TitulacaoAcademica titulacaoAcademica) {
        super(nome, cpf, emprestimosAtivos);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    public String getDepartamento() {
        return departamento;
    }

    public TitulacaoAcademica getTitulacaoAcademica() {
        return titulacaoAcademica;
    }

    public Docente converterParaDocente() {
        return new Docente(nome, cpf, emprestimosAtivos, departamento, titulacaoAcademica);
    }

    @Override
    public Usuario converterParaUsuario() {
        return converterParaDocente();
    }

    public static EntidadeDocente converterParaEntidade(Docente docente) {
        return new EntidadeDocente(
                docente.getNome(),
                docente.getCpf(),
                docente.getEmprestimosAtivos(),
                docente.getDepartamento(),
                docente.getTitulacaoAcademica());
    }
}

package domain;

import domain.enums.TitulacaoAcademica;

public class Docente extends Usuario {
    private String departamento;
    private TitulacaoAcademica titulacaoAcademica;

    public Docente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica) {
        super(cpf, nome);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public TitulacaoAcademica getTitulacaoAcademica() {
        return titulacaoAcademica;
    }

    public void setTitulacaoAcademica(TitulacaoAcademica titulacaoAcademica) {
        this.titulacaoAcademica = titulacaoAcademica;
    }

    @Override
    public boolean verificaLimiteEmprestimos() {
        int limiteEmprestimos = 8;
        return getEmprestimos().size() < limiteEmprestimos;
    }

    @Override
    public int calculaDiasAtraso(int diasPermanenciaLivro) {
        int prazoPermanencia = 30;
        int diasDeAtraso = diasPermanenciaLivro - prazoPermanencia;

        return Math.max(diasDeAtraso, 0);
    }

    @Override
    public Double calculaMulta(int diasDeAtraso) {
        if(diasDeAtraso == 0) return null;

        double multaPorDia = 1.00;

        return diasDeAtraso * multaPorDia;
    }
}

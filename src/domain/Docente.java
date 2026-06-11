package domain;

import domain.enums.TitulacaoAcademica;

/**
 * Representa um docente cadastrado na biblioteca.
 *
 * Docentes possuem departamento, titulação acadêmica
 * e regras específicas para empréstimos e multas.
 */
public class Docente extends Usuario {

    /** Departamento ao qual o docente pertence. */
    private String departamento;

    /** Titulação acadêmica do docente. */
    private TitulacaoAcademica titulacaoAcademica;

    /**
     * Cria um novo docente.
     *
     * @param cpf CPF do docente.
     * @param nome nome do docente.
     * @param departamento departamento do docente.
     * @param titulacaoAcademica titulação acadêmica.
     */
    public Docente(String cpf, String nome, String departamento,
                   TitulacaoAcademica titulacaoAcademica) {
        super(cpf, nome);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    /**
     * Cria um docente com empréstimos já registrados.
     *
     * @param nome nome do docente.
     * @param cpf CPF do docente.
     * @param emprestimosAtivos quantidade de empréstimos ativos.
     * @param departamento departamento do docente.
     * @param titulacaoAcademica titulação acadêmica.
     */
    public Docente(String nome, String cpf, int emprestimosAtivos,
                   String departamento,
                   TitulacaoAcademica titulacaoAcademica) {
        super(nome, cpf, emprestimosAtivos);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    /**
     * @return departamento do docente.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Define o departamento do docente.
     *
     * @param departamento novo departamento.
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @return titulação acadêmica do docente.
     */
    public TitulacaoAcademica getTitulacaoAcademica() {
        return titulacaoAcademica;
    }

    /**
     * Define a titulação acadêmica do docente.
     *
     * @param titulacaoAcademica nova titulação.
     */
    public void setTitulacaoAcademica(TitulacaoAcademica titulacaoAcademica) {
        this.titulacaoAcademica = titulacaoAcademica;
    }

    /**
     * Verifica se o docente ainda pode realizar empréstimos.
     *
     * @return true se estiver abaixo do limite.
     */
    @Override
    public boolean verificaLimiteEmprestimos() {
        int limiteEmprestimos = 8;
        return getEmprestimosAtivos() < limiteEmprestimos;
    }

    /**
     * Retorna o prazo de permanência de um livro.
     *
     * @return prazo em dias.
     */
    @Override
    public int prazoPermanencia() {
        return 30;
    }

    /**
     * Calcula os dias de atraso de uma devolução.
     *
     * @param diasPermanenciaLivro quantidade de dias que o livro permaneceu emprestado.
     * @return quantidade de dias de atraso.
     */
    @Override
    public int calculaDiasAtraso(int diasPermanenciaLivro) {
        int prazoPermanencia = prazoPermanencia();
        int diasDeAtraso = diasPermanenciaLivro - prazoPermanencia;

        return Math.max(diasDeAtraso, 0);
    }

    /**
     * Calcula a multa referente ao atraso.
     *
     * @param diasDeAtraso quantidade de dias em atraso.
     * @return valor da multa ou null caso não exista atraso.
     */
    @Override
    public Double calculaMulta(int diasDeAtraso) {
        if (diasDeAtraso == 0) {
            return null;
        }

        double multaPorDia = 1.00;

        return diasDeAtraso * multaPorDia;
    }
}package domain;

import domain.enums.TitulacaoAcademica;

public class Docente extends Usuario {
    private String departamento;
    private TitulacaoAcademica titulacaoAcademica;

    public Docente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica) {
        super(cpf, nome);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    public Docente(String nome, String cpf, int emprestimosAtivos, String departamento, TitulacaoAcademica titulacaoAcademica) {
        super(nome, cpf, emprestimosAtivos);
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
        return getEmprestimosAtivos() < limiteEmprestimos;
    }

    @Override
    public int prazoPermanencia() {
        return 30;
    }

    @Override
    public int calculaDiasAtraso(int diasPermanenciaLivro) {
        int prazoPermanencia = prazoPermanencia();
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

package domain;

import domain.enums.TitulacaoAcademica;

/**
 * Representa um docente no sistema, estendendo a classe {@link Usuario}.
 * Define regras específicas de biblioteca para membros do corpo docente,
 * incluindo limites de empréstimo estendidos, prazos de permanência diferenciados
 * e cálculo de multas específico.
 */
public class Docente extends Usuario {

    /**
     * Departamento ao qual o docente está vinculado.
     */
    private String departamento;

    /**
     * Titulação acadêmica do docente.
     */
    private TitulacaoAcademica titulacaoAcademica;

    /**
     * Construtor para inicialização básica do docente.
     *
     * @param cpf                o CPF do docente.
     * @param nome               o nome completo do docente.
     * @param departamento       o departamento de vinculação.
     * @param titulacaoAcademica a titulação acadêmica do docente.
     */
    public Docente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica) {
        super(cpf, nome);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    /**
     * Construtor completo para inicialização do docente com histórico de empréstimos.
     *
     * @param nome               o nome completo do docente.
     * @param cpf                o CPF do docente.
     * @param emprestimosAtivos  a quantidade de empréstimos pendentes.
     * @param departamento       o departamento de vinculação.
     * @param titulacaoAcademica a titulação acadêmica do docente.
     */
    public Docente(String nome, String cpf, int emprestimosAtivos, String departamento, TitulacaoAcademica titulacaoAcademica) {
        super(nome, cpf, emprestimosAtivos);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    /**
     * Obtém o departamento de vinculação do docente.
     *
     * @return o nome do departamento.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Define o departamento de vinculação do docente.
     *
     * @param departamento o nome do departamento.
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Obtém a titulação acadêmica do docente.
     *
     * @return a titulação acadêmica (enum {@link TitulacaoAcademica}).
     */
    public TitulacaoAcademica getTitulacaoAcademica() {
        return titulacaoAcademica;
    }

    /**
     * Define a titulação acadêmica do docente.
     *
     * @param titulacaoAcademica a titulação a ser definida.
     */
    public void setTitulacaoAcademica(TitulacaoAcademica titulacaoAcademica) {
        this.titulacaoAcademica = titulacaoAcademica;
    }

    /**
     * Verifica se o docente possui limite disponível para novos empréstimos.
     * O limite estabelecido para docentes é de 8 empréstimos.
     *
     * @return true se o número de empréstimos ativos for inferior a 8, false caso contrário.
     */
    @Override
    public boolean verificaLimiteEmprestimos() {
        int limiteEmprestimos = 8;
        return getEmprestimosAtivos() < limiteEmprestimos;
    }

    /**
     * Retorna o prazo padrão de permanência do item emprestado para docentes.
     *
     * @return o prazo em dias (30).
     */
    @Override
    public int prazoPermanencia() {
        return 30;
    }

    /**
     * Calcula a quantidade de dias de atraso com base no período de permanência permitido.
     *
     * @param diasPermanenciaLivro a quantidade total de dias que o livro ficou com o usuário.
     * @return o número de dias de atraso; retorna 0 caso não haja atraso.
     */
    @Override
    public int calculaDiasAtraso(int diasPermanenciaLivro) {
        int prazoPermanencia = prazoPermanencia();
        int diasDeAtraso = diasPermanenciaLivro - prazoPermanencia;

        return Math.max(diasDeAtraso, 0);
    }

    /**
     * Calcula o valor da multa com base nos dias de atraso.
     * O valor da multa é fixado em 1.00 por dia de atraso.
     *
     * @param diasDeAtraso a quantidade de dias em atraso.
     * @return o valor total da multa, ou null caso não haja atraso.
     */
    @Override
    public Double calculaMulta(int diasDeAtraso) {
        if (diasDeAtraso == 0) return null;

        double multaPorDia = 1.00;

        return diasDeAtraso * multaPorDia;
    }
}
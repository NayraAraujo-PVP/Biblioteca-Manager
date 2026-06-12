package domain;

/**
 * Representa um aluno no sistema, estendendo a classe {@link Usuario}.
 * Define regras específicas para limites de empréstimo, prazos de permanência
 * e cálculo de multas por atraso.
 */
public class Aluno extends Usuario {

    /**
     * Matrícula única de identificação do aluno na instituição.
     */
    private String matricula;

    /**
     * Construtor para inicialização básica do aluno.
     *
     * @param cpf       o CPF do aluno.
     * @param nome      o nome completo do aluno.
     * @param matricula a matrícula do aluno.
     */
    public Aluno(String cpf, String nome, String matricula) {
        super(cpf, nome);
        this.matricula = matricula;
    }

    /**
     * Construtor completo para inicialização do aluno com histórico de empréstimos.
     *
     * @param nome              o nome completo do aluno.
     * @param cpf               o CPF do aluno.
     * @param emprestimosAtivos a quantidade de empréstimos pendentes.
     * @param matricula         a matrícula do aluno.
     */
    public Aluno(String nome, String cpf, int emprestimosAtivos, String matricula) {
        super(nome, cpf, emprestimosAtivos);
        this.matricula = matricula;
    }

    /**
     * Obtém a matrícula do aluno.
     *
     * @return a matrícula.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Define a matrícula do aluno.
     *
     * @param matricula a nova matrícula a ser definida.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Verifica se o aluno ainda possui limite disponível para novos empréstimos.
     * O limite estabelecido para alunos é de 4 empréstimos.
     *
     * @return true se o número de empréstimos ativos for menor que o limite, false caso contrário.
     */
    @Override
    public boolean verificaLimiteEmprestimos() {
        int limiteEmprestimos = 4;
        return getEmprestimosAtivos() < limiteEmprestimos;
    }

    /**
     * Retorna o prazo padrão de permanência do item emprestado para alunos.
     *
     * @return o prazo em dias (14).
     */
    @Override
    public int prazoPermanencia() {
        return 14;
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
     * O valor da multa é fixado em 0.50 por dia de atraso.
     *
     * @param diasDeAtraso a quantidade de dias em atraso.
     * @return o valor total da multa, ou null caso não haja atraso.
     */
    @Override
    public Double calculaMulta(int diasDeAtraso) {
        if (diasDeAtraso == 0) return null;

        double multaPorDia = 0.50;

        return diasDeAtraso * multaPorDia;
    }
}
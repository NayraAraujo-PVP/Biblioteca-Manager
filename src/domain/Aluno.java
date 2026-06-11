package domain;

/**
 * Representa um aluno cadastrado no sistema da biblioteca.
 *
 * A classe herda as características básicas de um usuário
 * e adiciona a matrícula do aluno. Também implementa as
 * regras específicas de empréstimo, atraso e multa para alunos.
 *
 * Exemplo de uso:
 * <pre>
 * Aluno aluno = new Aluno("12345678900", "João Silva", "2023001");
 *
 * boolean podeEmprestar = aluno.verificaLimiteEmprestimos();
 * </pre>
 */
public class Aluno extends Usuario {

    /**
     * Matrícula do aluno.
     */
    private String matricula;

    /**
     * Cria um novo aluno.
     *
     * @param cpf CPF do aluno.
     * @param nome nome do aluno.
     * @param matricula matrícula do aluno.
     */
    public Aluno(String cpf, String nome, String matricula) {
        super(cpf, nome);
        this.matricula = matricula;
    }

    /**
     * Cria um novo aluno informando a quantidade de
     * empréstimos ativos.
     *
     * @param nome nome do aluno.
     * @param cpf CPF do aluno.
     * @param emprestimosAtivos quantidade de empréstimos ativos.
     * @param matricula matrícula do aluno.
     */
    public Aluno(String nome, String cpf, int emprestimosAtivos, String matricula) {
        super(nome, cpf, emprestimosAtivos);
        this.matricula = matricula;
    }

    /**
     * Retorna a matrícula do aluno.
     *
     * @return matrícula do aluno.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Define uma nova matrícula para o aluno.
     *
     * @param matricula nova matrícula.
     */
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    /**
     * Verifica se o aluno ainda pode realizar empréstimos.
     *
     * O limite máximo de empréstimos simultâneos para
     * alunos é de 4 livros.
     *
     * @return true caso o aluno esteja dentro do limite,
     * false caso contrário.
     */
    @Override
    public boolean verificaLimiteEmprestimos() {
        int limiteEmprestimos = 4;
        return getEmprestimosAtivos() < limiteEmprestimos;
    }

    /**
     * Calcula a quantidade de dias de atraso na devolução
     * de um livro.
     *
     * O prazo de empréstimo para alunos é de 14 dias.
     *
     * @param diasPermanenciaLivro quantidade de dias que o livro permaneceu com o aluno.
     * @return quantidade de dias de atraso. Retorna 0 caso não exista atraso.
     */
    @Override
    public int calculaDiasAtraso(int diasPermanenciaLivro) {
        int prazoPermanencia = 14;
        int diasDeAtraso = diasPermanenciaLivro - prazoPermanencia;

        return Math.max(diasDeAtraso, 0);
    }

    /**
     * Calcula o valor da multa com base nos dias de atraso.
     *
     * Para alunos, a multa é de R$ 0,50 por dia de atraso.
     *
     * @param diasDeAtraso quantidade de dias em atraso.
     * @return valor da multa calculada ou null caso não exista atraso.
     */
    @Override
    public Double calculaMulta(int diasDeAtraso) {
        if (diasDeAtraso == 0) return null;

        double multaPorDia = 0.50;

        return diasDeAtraso * multaPorDia;
    }
}

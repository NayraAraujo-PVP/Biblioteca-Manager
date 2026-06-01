package domain;

public class Aluno extends Usuario {
    private String matricula;

    public Aluno(String cpf, String nome, String matricula) {
        super(cpf, nome);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    @Override
    public boolean verificaLimiteEmprestimos() {
        int limiteEmprestimos = 4;
        return getEmprestimos().size() < limiteEmprestimos;
    }

    @Override
    public int calculaDiasAtraso(int diasPermanenciaLivro) {
        int prazoPermanencia = 14;
        int diasDeAtraso = diasPermanenciaLivro - prazoPermanencia;

        return Math.max(diasDeAtraso, 0);
    }

    @Override
    public Double calculaMulta(int diasDeAtraso) {
        if(diasDeAtraso == 0) return null;

        double multaPorDia = 0.50;

        return diasDeAtraso * multaPorDia;
    }
}

package domain;

public abstract class Usuario {
    private String nome;
    private final String cpf;
    private int emprestimosAtivos;

    protected Usuario(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        emprestimosAtivos = 0;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    public void addEmprestimo() {
        if(verificaLimiteEmprestimos()) {
            emprestimosAtivos++;
        }
    }

    public abstract boolean verificaLimiteEmprestimos();

    public void removeEmprestimo() {
        if(emprestimosAtivos > 0) {
            emprestimosAtivos--;
        }
    }

    public abstract int calculaDiasAtraso(int diasPermanenciaLivro);

    public abstract Double calculaMulta(int diasDeAtraso);
}

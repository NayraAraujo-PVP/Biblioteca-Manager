package domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
    private String nome;
    private final String cpf;
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    protected Usuario(String cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
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

    public List<Emprestimo> getEmprestimos() {
        return List.copyOf(emprestimos);
    }

    public void addEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

    public void removeEmprestimo(Emprestimo emprestimo) {
        emprestimos.remove(emprestimo);
    }

    public abstract int calculaDiasAtraso(int diasPermanenciaLivro);

    public abstract Double calculaMulta(int diasDeAtraso);
}

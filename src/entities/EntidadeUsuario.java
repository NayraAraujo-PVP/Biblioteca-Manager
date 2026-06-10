package entities;

import domain.Usuario;

public abstract class EntidadeUsuario {
    protected String nome;
    protected String cpf;
    protected int emprestimosAtivos;

    protected EntidadeUsuario() {}

    protected EntidadeUsuario(String nome, String cpf, int emprestimosAtivos) {
        this.nome = nome;
        this.cpf = cpf;
        this.emprestimosAtivos = emprestimosAtivos;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public int getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    public abstract Usuario converterParaUsuario();
}

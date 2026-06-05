package entities;

import domain.Usuario;

public abstract class EntidadeUsuario {
    protected final String nome;
    protected final String cpf;
    protected final int emprestimosAtivos;

    protected EntidadeUsuario(String nome, String cpf, int emprestimosAtivos) {
        this.nome = nome;
        this.cpf = cpf;
        this.emprestimosAtivos = emprestimosAtivos;
    }

    public abstract Usuario converterParaUsuario();
}

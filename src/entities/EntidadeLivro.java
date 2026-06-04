package entities;

import domain.Livro;

public class EntidadeLivro {
    private final int id;
    private final String titulo;
    private final String autor;
    private final String categoria;
    private final int quantidadeTotal;
    private final int quantidadeEmprestada;

    private EntidadeLivro(int id, String titulo, String autor, String categoria, int quantidadeTotal, int quantidadeEmprestada) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeEmprestada = quantidadeEmprestada;
    }

    public Livro converterParaLivro() {
        return new Livro(id, titulo, autor, categoria, quantidadeTotal, quantidadeEmprestada);
    }

    public static EntidadeLivro converterParaEntidade(Livro livro) {
        return new EntidadeLivro(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getCategoria(),
                livro.getQuantidadeTotal(),
                livro.getQuantidadeEmprestada());
    }
}

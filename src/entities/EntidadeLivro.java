package entities;

import domain.Livro;

public class EntidadeLivro {
    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private int quantidadeTotal;
    private int quantidadeEmprestada;

    public EntidadeLivro() {}

    private EntidadeLivro(int id, String titulo, String autor, String categoria, int quantidadeTotal, int quantidadeEmprestada) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.quantidadeTotal = quantidadeTotal;
        this.quantidadeEmprestada = quantidadeEmprestada;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal;
    }

    public int getQuantidadeEmprestada() {
        return quantidadeEmprestada;
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

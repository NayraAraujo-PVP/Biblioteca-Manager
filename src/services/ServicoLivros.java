package services;

import domain.Livro;
import repository.RepositorioLivros;

import java.util.List;
import java.util.Optional;

public class ServicoLivros {
    private final RepositorioLivros repositorioLivros;

    public ServicoLivros(RepositorioLivros repositorioLivros) {
        this.repositorioLivros = repositorioLivros;
    }

    public List<Livro> buscar(String termoPesquisa) {
        return repositorioLivros.buscar(termoPesquisa);
    }

    public Optional<Livro> buscarPorId(int id) {
        return repositorioLivros.buscar(id);
    }

    public void cadastrarLivro(String titulo, String autor, String categoria, int quantidadeTotal) {
        int id = repositorioLivros.getProximoId();
        Livro livro = new Livro(id, titulo, autor, categoria, quantidadeTotal);

        repositorioLivros.salvar(livro);
    }

    public void alteraQuantidadeTotal(int id, int quantidadeTotal) {
        Optional<Livro> livroOpt = repositorioLivros.buscar(id);

        if (livroOpt.isEmpty()) return;

        Livro livro = livroOpt.get();

        if(quantidadeTotal < livro.getQuantidadeEmprestada()) return;

        livro.setQuantidadeTotal(quantidadeTotal);

        repositorioLivros.salvar(livro);
    }

    public void editarLivro(int id, String titulo, String autor, String categoria) {
        Optional<Livro> livroOpt = repositorioLivros.buscar(id);

        if (livroOpt.isEmpty()) return;

        Livro livro = livroOpt.get();

        livro.setTitulo(titulo);
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        repositorioLivros.salvar(livro);
    }
}

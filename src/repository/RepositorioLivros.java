package repository;

import datamanager.DataManager;
import domain.Livro;
import entities.EntidadeLivro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RepositorioLivros {
    private final Map<Integer, EntidadeLivro> livroMap = new HashMap<>();

    private final DataManager dataManager;

    private static final String LIVROS_FILENAME = "livros";

    public RepositorioLivros(DataManager dataManager) {
        this.dataManager = dataManager;

        List<EntidadeLivro> entidadeLivroList = dataManager.buscar(LIVROS_FILENAME, EntidadeLivro.class);
        for (EntidadeLivro entidadeLivro : entidadeLivroList) {
            livroMap.put(entidadeLivro.getId(), entidadeLivro);
        }
    }

    public void salvar(Livro livro) {
        livroMap.put(livro.getId(), EntidadeLivro.converterParaEntidade(livro));

        dataManager.salvar(LIVROS_FILENAME, livroMap.values());
    }

    public Optional<Livro> buscar(int id) {
        return Optional.ofNullable(livroMap.get(id)).map(EntidadeLivro::converterParaLivro);
    }

    public List<Livro> buscar(String termoPesquisa) {
        return livroMap.values().stream()
                .filter(entidadeLivro -> entidadeLivro.getQuantidadeTotal() > 0)
                .filter(entidadeLivro -> entidadeLivro.getTitulo().toLowerCase().contains(termoPesquisa.toLowerCase())
                    || entidadeLivro.getAutor().toLowerCase().contains(termoPesquisa.toLowerCase())
                    || entidadeLivro.getCategoria().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeLivro::converterParaLivro)
                .toList();
    }

    public int getProximoId() {
        return livroMap.keySet().stream().mapToInt(o -> o).max().orElse(0) + 1;
    }
}

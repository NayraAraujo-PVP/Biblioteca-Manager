package repository;

import datamanager.DataManager;
import domain.Livro;
import entities.EntidadeLivro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioLivros {
    private final Map<Integer, EntidadeLivro> livroMap = new HashMap<>();

    private final DataManager dataManager;

    private static final String LIVROS_FILENAME = "livros";

    public RepositorioLivros(DataManager dataManager) {
        this.dataManager = dataManager;

        List<EntidadeLivro> entidadeLivroList = dataManager.buscar(LIVROS_FILENAME);
        for (EntidadeLivro entidadeLivro : entidadeLivroList) {
            livroMap.put(entidadeLivro.getId(), entidadeLivro);
        }
    }

    public void salvar(Livro livro) {
        livroMap.put(livro.getId(), EntidadeLivro.converterParaEntidade(livro));

        dataManager.salvar(LIVROS_FILENAME, livroMap.values());
    }

    public boolean contemId(int id) {
        return livroMap.containsKey(id);
    }

    public Livro buscar(int id) {
        return livroMap.get(id).converterParaLivro();
    }

    public int getProximoId() {
        return livroMap.keySet().stream().mapToInt(o -> o).max().orElse(0);
    }
}

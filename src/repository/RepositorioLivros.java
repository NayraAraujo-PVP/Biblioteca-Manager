package repository;

import domain.Livro;

import java.util.HashMap;
import java.util.Map;

public class RepositorioLivros {
    private final Map<Integer, Livro> livroMap = new HashMap<>();

    public void salvar(Livro livro) {
        livroMap.put(livro.getId(), livro);
    }

    public boolean contemId(int id) {
        return livroMap.containsKey(id);
    }

    public Livro buscar(int id) {
        return livroMap.get(id);
    }

    public int getProximoId() {
        return livroMap.keySet().stream().mapToInt(o -> o).max().orElse(0);
    }
}

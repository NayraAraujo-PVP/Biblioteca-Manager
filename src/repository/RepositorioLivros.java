package repository;

import domain.Livro;
import entities.EntidadeLivro;

import java.util.HashMap;
import java.util.Map;

public class RepositorioLivros {
    private final Map<Integer, EntidadeLivro> livroMap = new HashMap<>();

    public void salvar(Livro livro) {
        livroMap.put(livro.getId(), EntidadeLivro.converterParaEntidade(livro));
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

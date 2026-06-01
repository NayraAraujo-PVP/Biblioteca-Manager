package repository;

import domain.Emprestimo;

import java.util.HashMap;
import java.util.Map;

public class RepositorioEmprestimos {
    private final Map<Integer, Emprestimo> emprestimoMap = new HashMap<>();

    public void salvar(Emprestimo emprestimo) {
        emprestimoMap.put(emprestimo.getId(), emprestimo);
    }

    public boolean contemId(int id) {
        return emprestimoMap.containsKey(id);
    }

    public Emprestimo buscar(int id) {
        return emprestimoMap.get(id);
    }

    public int getProximoId() {
        return emprestimoMap.keySet().stream().mapToInt(o -> o).max().orElse(0);
    }
}

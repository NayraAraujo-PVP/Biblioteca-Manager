package repository;

import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import entities.EntidadeEmprestimo;

import java.util.HashMap;
import java.util.Map;

public class RepositorioEmprestimos {
    private final Map<Integer, EntidadeEmprestimo> emprestimoMap = new HashMap<>();

    private final RepositorioLivros repositorioLivros;
    private final RepositorioUsuarios repositorioUsuarios;

    public RepositorioEmprestimos(RepositorioLivros repositorioLivros, RepositorioUsuarios repositorioUsuarios) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public void salvar(Emprestimo emprestimo) {
        repositorioLivros.salvar(emprestimo.getLivro());
        repositorioUsuarios.salvar(emprestimo.getUsuario());
        emprestimoMap.put(emprestimo.getId(), EntidadeEmprestimo.converterParaEntidade(emprestimo));
    }

    public boolean contemId(int id) {
        return emprestimoMap.containsKey(id);
    }

    public Emprestimo buscar(int id) {
        EntidadeEmprestimo entidadeEmprestimo = emprestimoMap.get(id);

        Usuario usuario = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
        Livro livro = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());

        return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
    }

    public int getProximoId() {
        return emprestimoMap.keySet().stream().mapToInt(o -> o).max().orElse(0);
    }
}

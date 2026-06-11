package repository;

import datamanager.DataManager;
import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import entities.EntidadeEmprestimo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioEmprestimos {
    private final Map<Integer, EntidadeEmprestimo> emprestimoMap = new HashMap<>();

    private final RepositorioLivros repositorioLivros;
    private final RepositorioUsuarios repositorioUsuarios;

    private final DataManager dataManager;

    private static final String EMPRESTIMOS_FILENAME = "emprestimos";

    public RepositorioEmprestimos(RepositorioLivros repositorioLivros, RepositorioUsuarios repositorioUsuarios, DataManager dataManager) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioUsuarios = repositorioUsuarios;
        this.dataManager = dataManager;

        List<EntidadeEmprestimo> entidadeEmprestimoList = dataManager.buscar(EMPRESTIMOS_FILENAME, EntidadeEmprestimo.class);
        for (EntidadeEmprestimo entidadeEmprestimo : entidadeEmprestimoList) {
            emprestimoMap.put(entidadeEmprestimo.getId(), entidadeEmprestimo);
        }
    }

    public void salvar(Emprestimo emprestimo) {
        repositorioLivros.salvar(emprestimo.getLivro());
        repositorioUsuarios.salvar(emprestimo.getUsuario());
        emprestimoMap.put(emprestimo.getId(), EntidadeEmprestimo.converterParaEntidade(emprestimo));

        dataManager.salvar(EMPRESTIMOS_FILENAME, emprestimoMap.values());
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
        return emprestimoMap.keySet().stream().mapToInt(o -> o).max().orElse(0) + 1;
    }

    public List<Emprestimo> buscarEmprestimosPara(Usuario usuario) {
        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> !entidadeEmprestimo.isDevolvido())
                .filter(entidadeEmprestimo -> entidadeEmprestimo.getCpfUsuario().equals(usuario.getCpf()))
                .map(entidadeEmprestimo -> {
                    Livro livro = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }
}

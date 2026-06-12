package repository;

import datamanager.DataManager;
import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import entities.EntidadeEmprestimo;
import utils.DateUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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

    public List<Emprestimo> buscarEmprestimosPara(Usuario usuario, boolean somenteAtivos) {
        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> !somenteAtivos || !entidadeEmprestimo.isDevolvido())
                .filter(entidadeEmprestimo -> entidadeEmprestimo.getCpfUsuario().equals(usuario.getCpf()))
                .map(entidadeEmprestimo -> {
                    Livro livro = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }

    public List<Emprestimo> buscarPorDataRetirada(LocalDate localDate) {
        Instant inicio = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant fim = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> {
                    Instant retirada = DateUtils.converterDeString(entidadeEmprestimo.getDataRetirada()).toInstant();
                    return retirada.isAfter(inicio) && retirada.isBefore(fim);
                })
                .map(entidadeEmprestimo -> {
                    Usuario usuario = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
                    Livro livro = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }

    public List<Emprestimo> buscarDataDevolucao(LocalDate localDate) {
        Instant inicio = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant fim = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> {
                    if(!entidadeEmprestimo.isDevolvido()) return false;
                    Instant devolvido = DateUtils.converterDeString(entidadeEmprestimo.getDataDevolucao()).toInstant();
                    return devolvido.isAfter(inicio) && devolvido.isBefore(fim);
                })
                .map(entidadeEmprestimo -> {
                    Usuario usuario = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
                    Livro livro = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }

    public List<Emprestimo> buscaEmprestimoPorLivro(Livro livro) {
        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> entidadeEmprestimo.getIdLivro() == livro.getId() && !entidadeEmprestimo.isDevolvido())
                .map(entidadeEmprestimo -> {
                    Usuario usuario = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }
}

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
import java.util.Optional;

/**
 * Repositório responsável pelo gerenciamento da persistência e recuperação de instâncias de {@link Emprestimo}.
 * Atua como uma camada de intermediação entre os objetos de domínio e a persistência de dados,
 * orquestrando a relação entre usuários, livros e os registros de empréstimos.
 */
public class RepositorioEmprestimos {

    private final Map<Integer, EntidadeEmprestimo> emprestimoMap = new HashMap<>();
    private final RepositorioLivros repositorioLivros;
    private final RepositorioUsuarios repositorioUsuarios;
    private final DataManager dataManager;

    private static final String EMPRESTIMOS_FILENAME = "emprestimos";

    /**
     * Constrói o repositório, carregando os dados persistidos através do {@link DataManager}.
     *
     * @param repositorioLivros   instância do repositório de livros.
     * @param repositorioUsuarios instância do repositório de usuários.
     * @param dataManager         instância do gerenciador de dados para persistência em arquivo.
     */
    public RepositorioEmprestimos(RepositorioLivros repositorioLivros, RepositorioUsuarios repositorioUsuarios, DataManager dataManager) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioUsuarios = repositorioUsuarios;
        this.dataManager = dataManager;

        List<EntidadeEmprestimo> entidadeEmprestimoList = dataManager.buscar(EMPRESTIMOS_FILENAME, EntidadeEmprestimo.class);
        for (EntidadeEmprestimo entidadeEmprestimo : entidadeEmprestimoList) {
            emprestimoMap.put(entidadeEmprestimo.getId(), entidadeEmprestimo);
        }
    }

    /**
     * Salva ou atualiza um registro de empréstimo, persistindo também as alterações de estado
     * nos repositórios associados (usuário e livro).
     *
     * @param emprestimo o empréstimo a ser salvo.
     */
    public void salvar(Emprestimo emprestimo) {
        repositorioLivros.salvar(emprestimo.getLivro());
        repositorioUsuarios.salvar(emprestimo.getUsuario());
        emprestimoMap.put(emprestimo.getId(), EntidadeEmprestimo.converterParaEntidade(emprestimo));

        dataManager.salvar(EMPRESTIMOS_FILENAME, emprestimoMap.values());
    }

    /**
     * Busca um empréstimo pelo seu identificador único.
     *
     * @param id identificador do empréstimo.
     * @return um {@link Optional} contendo o empréstimo se encontrado, ou vazio caso contrário.
     */
    public Optional<Emprestimo> buscar(int id) {
        EntidadeEmprestimo entidadeEmprestimo = emprestimoMap.get(id);

        if (entidadeEmprestimo == null) return Optional.empty();

        Optional<Usuario> usuarioOpt = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
        Optional<Livro> livroOpt = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());

        if (usuarioOpt.isEmpty() || livroOpt.isEmpty()) return Optional.empty();

        Usuario usuario = usuarioOpt.get();
        Livro livro = livroOpt.get();

        return Optional.of(entidadeEmprestimo.converterParaEmprestimo(usuario, livro));
    }

    /**
     * Calcula o próximo identificador disponível para um novo empréstimo.
     *
     * @return o próximo ID incremental.
     */
    public int getProximoId() {
        return emprestimoMap.keySet().stream().mapToInt(o -> o).max().orElse(0) + 1;
    }

    /**
     * Recupera todos os empréstimos associados a um determinado usuário.
     *
     * @param usuario       o usuário alvo.
     * @param somenteAtivos se true, filtra apenas empréstimos ainda não devolvidos.
     * @return lista de empréstimos encontrados.
     */
    public List<Emprestimo> buscarEmprestimosPara(Usuario usuario, boolean somenteAtivos) {
        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> !somenteAtivos || !entidadeEmprestimo.isDevolvido())
                .filter(entidadeEmprestimo -> entidadeEmprestimo.getCpfUsuario().equals(usuario.getCpf()))
                .map(entidadeEmprestimo -> {
                    Optional<Livro> livroOpt = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    if (livroOpt.isEmpty()) return null;
                    Livro livro = livroOpt.get();

                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }

    /**
     * Busca empréstimos realizados em uma data específica.
     *
     * @param localDate a data de referência.
     * @return lista de empréstimos com data de retirada correspondente à data informada.
     */
    public List<Emprestimo> buscarPorDataRetirada(LocalDate localDate) {
        Instant inicio = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant fim = localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> {
                    Instant retirada = DateUtils.converterDeString(entidadeEmprestimo.getDataRetirada()).toInstant();
                    return retirada.isAfter(inicio) && retirada.isBefore(fim);
                })
                .map(entidadeEmprestimo -> {
                    Optional<Usuario> usuarioOpt = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
                    if (usuarioOpt.isEmpty()) return null;
                    Usuario usuario = usuarioOpt.get();

                    Optional<Livro> livroOpt = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    if (livroOpt.isEmpty()) return null;
                    Livro livro = livroOpt.get();

                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }

    /**
     * Busca empréstimos devolvidos em uma data específica.
     *
     * @param localDate a data de referência.
     * @return lista de empréstimos com data de devolução correspondente à data informada.
     */
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
                    Optional<Usuario> usuarioOpt = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
                    if (usuarioOpt.isEmpty()) return null;
                    Usuario usuario = usuarioOpt.get();

                    Optional<Livro> livroOpt = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    if (livroOpt.isEmpty()) return null;
                    Livro livro = livroOpt.get();

                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }

    /**
     * Busca empréstimos ativos para um determinado livro.
     *
     * @param livro o livro alvo da busca.
     * @return lista de empréstimos em aberto para o livro.
     */
    public List<Emprestimo> buscaEmprestimoPorLivro(Livro livro) {
        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> entidadeEmprestimo.getIdLivro() == livro.getId() && !entidadeEmprestimo.isDevolvido())
                .map(entidadeEmprestimo -> {
                    Optional<Usuario> usuarioOpt = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
                    if (usuarioOpt.isEmpty()) return null;
                    Usuario usuario = usuarioOpt.get();

                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }
}
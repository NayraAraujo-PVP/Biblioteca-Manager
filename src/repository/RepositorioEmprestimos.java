package repository;

import datamanager.DataManager;
import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import entities.EntidadeEmprestimo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositório responsável pelo armazenamento e recuperação
 * dos empréstimos da biblioteca.
 */
public class RepositorioEmprestimos {

    private final Map<Integer, EntidadeEmprestimo> emprestimoMap = new HashMap<>();

    private final RepositorioLivros repositorioLivros;
    private final RepositorioUsuarios repositorioUsuarios;
    private final DataManager dataManager;

    private static final String EMPRESTIMOS_FILENAME = "emprestimos";

    /**
     * Inicializa o repositório carregando os empréstimos salvos.
     *
     * @param repositorioLivros repositório de livros.
     * @param repositorioUsuarios repositório de usuários.
     * @param dataManager gerenciador de persistência.
     */
    public RepositorioEmprestimos(RepositorioLivros repositorioLivros,
                                  RepositorioUsuarios repositorioUsuarios,
                                  DataManager dataManager) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioUsuarios = repositorioUsuarios;
        this.dataManager = dataManager;

        List<EntidadeEmprestimo> entidadeEmprestimoList =
                dataManager.buscar(EMPRESTIMOS_FILENAME, EntidadeEmprestimo.class);

        for (EntidadeEmprestimo entidadeEmprestimo : entidadeEmprestimoList) {
            emprestimoMap.put(entidadeEmprestimo.getId(), entidadeEmprestimo);
        }
    }

    /**
     * Salva um empréstimo.
     *
     * @param emprestimo empréstimo a ser salvo.
     */
    public void salvar(Emprestimo emprestimo) {
        repositorioLivros.salvar(emprestimo.getLivro());
        repositorioUsuarios.salvar(emprestimo.getUsuario());

        emprestimoMap.put(
                emprestimo.getId(),
                EntidadeEmprestimo.converterParaEntidade(emprestimo)
        );

        dataManager.salvar(EMPRESTIMOS_FILENAME, emprestimoMap.values());
    }

    /**
     * Verifica se existe um empréstimo com o id informado.
     *
     * @param id identificador do empréstimo.
     * @return true se o empréstimo existir.
     */
    public boolean contemId(int id) {
        return emprestimoMap.containsKey(id);
    }

    /**
     * Busca um empréstimo pelo id.
     *
     * @param id identificador do empréstimo.
     * @return empréstimo encontrado.
     */
    public Emprestimo buscar(int id) {
        EntidadeEmprestimo entidadeEmprestimo = emprestimoMap.get(id);

        Usuario usuario =
                repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());

        Livro livro =
                repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());

        return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
    }

    /**
     * Obtém o próximo id disponível para empréstimo.
     *
     * @return próximo identificador.
     */
    public int getProximoId() {
        return emprestimoMap.keySet()
                .stream()
                .mapToInt(o -> o)
                .max()
                .orElse(0) + 1;
    }

    /**
     * Busca os empréstimos ativos de um usuário.
     *
     * @param usuario usuário a ser consultado.
     * @return lista de empréstimos não devolvidos.
     */
    public List<Emprestimo> buscarEmprestimosPara(Usuario usuario) {
        return emprestimoMap.values().stream()
                .filter(entidadeEmprestimo -> !entidadeEmprestimo.isDevolvido())
                .filter(entidadeEmprestimo ->
                        entidadeEmprestimo.getCpfUsuario().equals(usuario.getCpf()))
                .map(entidadeEmprestimo -> {
                    Livro livro =
                            repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());

                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }
}

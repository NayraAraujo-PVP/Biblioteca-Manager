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

/**
 * Classe responsável pelo armazenamento e gerenciamento dos registros de empréstimos.
 * Ela atua mantendo os dados em memória através de um Map para consultas rápidas, 
 * além de coordenar a persistência desses dados em arquivo utilizando o DataManager.
 */
public class RepositorioEmprestimos {
    
    // Mapa que guarda os empréstimos em memória usando o ID como chave
    private final Map<Integer, EntidadeEmprestimo> emprestimoMap = new HashMap<>();

    // Dependências necessárias para reconstruir objetos completos
    private final RepositorioLivros repositorioLivros;
    private final RepositorioUsuarios repositorioUsuarios;
    private final DataManager dataManager;

    private static final String EMPRESTIMOS_FILENAME = "emprestimos";

    /**
     * Construtor do repositório.
     * Além de injetar as dependências, ele já realiza a leitura do arquivo de persistência
     * e carrega todos os empréstimos salvos anteriormente para a memória (no map).
     * * @param repositorioLivros   Repositório para buscar os dados dos livros emprestados.
     * @param repositorioUsuarios Repositório para buscar os dados dos usuários que fizeram o empréstimo.
     * @param dataManager         Gerenciador de dados responsável pela leitura e escrita em arquivo.
     */
    public RepositorioEmprestimos(RepositorioLivros repositorioLivros, RepositorioUsuarios repositorioUsuarios, DataManager dataManager) {
        this.repositorioLivros = repositorioLivros;
        this.repositorioUsuarios = repositorioUsuarios;
        this.dataManager = dataManager;

        // Carrega os dados salvos no arquivo JSON e preenche o Map
        List<EntidadeEmprestimo> entidadeEmprestimoList = dataManager.buscar(EMPRESTIMOS_FILENAME, EntidadeEmprestimo.class);
        for (EntidadeEmprestimo entidadeEmprestimo : entidadeEmprestimoList) {
            emprestimoMap.put(entidadeEmprestimo.getId(), entidadeEmprestimo);
        }
    }

    /**
     * Salva ou atualiza um registro de empréstimo.
     * O método também garante que o status atualizado do livro e do usuário associados 
     * sejam salvos nos seus respectivos repositórios antes de persistir o empréstimo em si.
     * * @param emprestimo O objeto de domínio Emprestimo a ser salvo.
     */
    public void salvar(Emprestimo emprestimo) {
        repositorioLivros.salvar(emprestimo.getLivro());
        repositorioUsuarios.salvar(emprestimo.getUsuario());
        emprestimoMap.put(emprestimo.getId(), EntidadeEmprestimo.converterParaEntidade(emprestimo));

        dataManager.salvar(EMPRESTIMOS_FILENAME, emprestimoMap.values());
    }

    /**
     * Verifica se um empréstimo com o ID informado já existe no repositório em memória.
     * * @param id O identificador do empréstimo.
     * @return true se o empréstimo existir, false caso contrário.
     */
    public boolean contemId(int id) {
        return emprestimoMap.containsKey(id);
    }

    /**
     * Busca um empréstimo específico pelo seu ID.
     * Ele recupera a entidade base armazenada e utiliza os repositórios de usuários e livros 
     * para reconstruir e retornar o objeto de domínio completo.
     * * @param id O identificador do empréstimo procurado.
     * @return O objeto Emprestimo montado com todas as suas dependências.
     */
    public Emprestimo buscar(int id) {
        EntidadeEmprestimo entidadeEmprestimo = emprestimoMap.get(id);

        Usuario usuario = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
        Livro livro = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());

        return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
    }

    /**
     * Gera e retorna o próximo ID disponível para o cadastro de um novo empréstimo.
     * A lógica busca o maior ID atualmente cadastrado no mapa e incrementa em 1.
     * * @return O próximo ID inteiro sequencial.
     */
    public int getProximoId() {
        return emprestimoMap.keySet().stream().mapToInt(o -> o).max().orElse(0) + 1;
    }

    /**
     * Retorna uma lista com todos os empréstimos ativos (ainda não devolvidos) 
     * que estão associados a um usuário específico.
     * * @param usuario O usuário do qual se deseja buscar os empréstimos pendentes.
     * @return Uma lista de objetos Emprestimo.
     */
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

    /**
     * Filtra e retorna todos os empréstimos que foram realizados (retirados) em uma data específica.
     * Realiza a conversão de LocalDate para Instant para garantir a precisão da comparação ao longo do dia.
     * * @param localDate A data de retirada que servirá como filtro.
     * @return Uma lista de empréstimos realizados nessa data.
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
                    Usuario usuario = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
                    Livro livro = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }

    /**
     * Filtra e retorna todos os empréstimos que já foram concluídos (devolvidos) 
     * e cuja data de devolução coincida com a data informada.
     * * @param localDate A data de devolução que servirá como filtro.
     * @return Uma lista de empréstimos devolvidos nessa data.
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
                    Usuario usuario = repositorioUsuarios.buscarUsuario(entidadeEmprestimo.getCpfUsuario());
                    Livro livro = repositorioLivros.buscar(entidadeEmprestimo.getIdLivro());
                    return entidadeEmprestimo.converterParaEmprestimo(usuario, livro);
                })
                .toList();
    }
}
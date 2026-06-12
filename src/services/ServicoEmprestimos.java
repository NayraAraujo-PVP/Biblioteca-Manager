package services;

import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import repository.RepositorioEmprestimos;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela orquestração das regras de negócio relacionadas aos empréstimos.
 * Atua como uma fachada para operações de empréstimo, devolução e consultas específicas
 * no repositório de empréstimos.
 */
public class ServicoEmprestimos {

    private final RepositorioEmprestimos repositorioEmprestimos;

    /**
     * Constrói o serviço com a instância do repositório necessária.
     *
     * @param repositorioEmprestimos repositório de empréstimos a ser utilizado.
     */
    public ServicoEmprestimos(RepositorioEmprestimos repositorioEmprestimos) {
        this.repositorioEmprestimos = repositorioEmprestimos;
    }

    /**
     * Busca empréstimos associados a um usuário.
     *
     * @param usuario       o usuário alvo.
     * @param somenteAtivos se true, retorna apenas empréstimos ainda não devolvidos.
     * @return lista de empréstimos encontrados.
     */
    public List<Emprestimo> buscarEmprestimosPara(Usuario usuario, boolean somenteAtivos) {
        return repositorioEmprestimos.buscarEmprestimosPara(usuario, somenteAtivos);
    }

    /**
     * Busca empréstimos realizados em uma data específica.
     *
     * @param localDate a data de retirada.
     * @return lista de empréstimos encontrados.
     */
    public List<Emprestimo> buscarPorDataRetirada(LocalDate localDate) {
        return repositorioEmprestimos.buscarPorDataRetirada(localDate);
    }

    /**
     * Busca empréstimos devolvidos em uma data específica.
     *
     * @param localDate a data de devolução.
     * @return lista de empréstimos encontrados.
     */
    public List<Emprestimo> buscarDataDevolucao(LocalDate localDate) {
        return repositorioEmprestimos.buscarDataDevolucao(localDate);
    }

    /**
     * Busca empréstimos ativos para um livro específico.
     *
     * @param livro o livro alvo.
     * @return lista de empréstimos encontrados.
     */
    public List<Emprestimo> buscaEmprestimoPorLivro(Livro livro) {
        return repositorioEmprestimos.buscaEmprestimoPorLivro(livro);
    }

    /**
     * Realiza a operação de empréstimo de um livro para um usuário, validando as regras de 
     * disponibilidade do livro e limite de empréstimos do usuário.
     *
     * @param usuario o usuário que deseja realizar o empréstimo.
     * @param livro   o livro a ser emprestado.
     * @return um {@link Optional} contendo o empréstimo criado, ou vazio se as regras não forem atendidas.
     */
    public Optional<Emprestimo> realizarEmprestimo(Usuario usuario, Livro livro) {
        if(livro.verificaDisponivel() && usuario.verificaLimiteEmprestimos()) {
            int id = repositorioEmprestimos.getProximoId();
            Emprestimo emprestimo = new Emprestimo(id, usuario, livro, new Date());

            usuario.addEmprestimo();
            livro.adicionarUmEmprestado();

            repositorioEmprestimos.salvar(emprestimo);

            return Optional.of(emprestimo);
        }

        return Optional.empty();
    }

    /**
     * Registra a devolução de um livro a partir do identificador do empréstimo.
     * Atualiza o estado dos objetos de domínio e persiste as alterações.
     *
     * @param id identificador do empréstimo.
     * @return um {@link Optional} contendo o empréstimo atualizado, ou vazio se o empréstimo não for encontrado ou já tiver sido devolvido.
     */
    public Optional<Emprestimo> realizarDevolucao(int id) {
        Optional<Emprestimo> emprestimoOpt = repositorioEmprestimos.buscar(id);

        if (emprestimoOpt.isEmpty()) return emprestimoOpt;

        Emprestimo emprestimo = emprestimoOpt.get();

        if(emprestimo.isDevolvido()) return Optional.empty();

        emprestimo.registrarDevolucao(new Date());

        emprestimo.getUsuario().removeEmprestimo();
        emprestimo.getLivro().retirarUmEmprestado();

        repositorioEmprestimos.salvar(emprestimo);

        return Optional.of(emprestimo);
    }
}
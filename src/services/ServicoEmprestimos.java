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
 * Serviço responsável pelo gerenciamento de empréstimos
 * e devoluções de livros da biblioteca.
 */
public class ServicoEmprestimos {

    private final RepositorioEmprestimos repositorioEmprestimos;

    /**
     * Cria uma instância do serviço de empréstimos.
     *
     * @param repositorioEmprestimos repositório de empréstimos.
     */
    public ServicoEmprestimos(RepositorioEmprestimos repositorioEmprestimos) {
        this.repositorioEmprestimos = repositorioEmprestimos;
    }

    public List<Emprestimo> buscarEmprestimosPara(Usuario usuario, boolean somenteAtivos) {
        return repositorioEmprestimos.buscarEmprestimosPara(usuario, somenteAtivos);
    }

    public List<Emprestimo> buscarPorDataRetirada(LocalDate localDate) {
        return repositorioEmprestimos.buscarPorDataRetirada(localDate);
    }

    public List<Emprestimo> buscarDataDevolucao(LocalDate localDate) {
        return repositorioEmprestimos.buscarDataDevolucao(localDate);
    }

    public List<Emprestimo> buscaEmprestimoPorLivro(Livro livro) {
        return repositorioEmprestimos.buscaEmprestimoPorLivro(livro);
    /**
     * Busca os empréstimos ativos de um usuário.
     *
     * @param usuario usuário consultado.
     * @return lista de empréstimos encontrados.
     */
    public List<Emprestimo> buscarEmprestimosPara(Usuario usuario) {
        return repositorioEmprestimos.buscarEmprestimosPara(usuario);
    }

    /**
     * Realiza um novo empréstimo de livro.
     *
     * @param usuario usuário que realizará o empréstimo.
     * @param livro livro a ser emprestado.
     * @return empréstimo criado, caso a operação seja válida.
     */
    public Optional<Emprestimo> realizarEmprestimo(Usuario usuario, Livro livro) {
        if (livro.verificaDisponivel() && usuario.verificaLimiteEmprestimos()) {
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
     * Registra a devolução de um empréstimo.
     *
     * @param id identificador do empréstimo.
     * @return empréstimo atualizado, caso exista e não tenha sido devolvido.
     */
    public Optional<Emprestimo> realizarDevolucao(int id) {
        if (!repositorioEmprestimos.contemId(id)) {
            return Optional.empty();
        }

        Emprestimo emprestimo = repositorioEmprestimos.buscar(id);

        if (emprestimo.isDevolvido()) {
            return Optional.empty();
        }

        emprestimo.registrarDevolucao(new Date());

        emprestimo.getUsuario().removeEmprestimo();
        emprestimo.getLivro().retirarUmEmprestado();

        repositorioEmprestimos.salvar(emprestimo);

        return Optional.of(emprestimo);
    }
}

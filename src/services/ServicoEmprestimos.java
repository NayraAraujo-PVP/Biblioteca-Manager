package services;

import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import repository.RepositorioEmprestimos;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ServicoEmprestimos {
    private final RepositorioEmprestimos repositorioEmprestimos;

    public ServicoEmprestimos(RepositorioEmprestimos repositorioEmprestimos) {
        this.repositorioEmprestimos = repositorioEmprestimos;
    }

    public List<Emprestimo> buscarEmprestimosPara(Usuario usuario) {
        return repositorioEmprestimos.buscarEmprestimosPara(usuario);
    }

    public List<Emprestimo> buscarPorDataRetirada(LocalDate localDate) {
        return repositorioEmprestimos.buscarPorDataRetirada(localDate);
    }

    public List<Emprestimo> buscarDataDevolucao(LocalDate localDate) {
        return repositorioEmprestimos.buscarDataDevolucao(localDate);
    }

    public List<Emprestimo> buscaEmprestimoPorLivro(Livro livro) {
        return repositorioEmprestimos.buscaEmprestimoPorLivro(livro);
    }

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

    public Optional<Emprestimo> realizarDevolucao(int id) {
        if(!repositorioEmprestimos.contemId(id)) return Optional.empty();

        Emprestimo emprestimo = repositorioEmprestimos.buscar(id);

        if(emprestimo.isDevolvido()) return Optional.empty();

        emprestimo.registrarDevolucao(new Date());

        emprestimo.getUsuario().removeEmprestimo();
        emprestimo.getLivro().retirarUmEmprestado();

        repositorioEmprestimos.salvar(emprestimo);

        return Optional.of(emprestimo);
    }
}

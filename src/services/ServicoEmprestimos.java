package services;

import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import repository.RepositorioEmprestimos;

import java.util.Date;

public class ServicoEmprestimos {
    private final RepositorioEmprestimos repositorioEmprestimos;

    public ServicoEmprestimos(RepositorioEmprestimos repositorioEmprestimos) {
        this.repositorioEmprestimos = repositorioEmprestimos;
    }

    public void realizarEmprestimo(Usuario usuario, Livro livro) {
        if(livro.verificaDisponivel() && usuario.verificaLimiteEmprestimos()) {
            int id = repositorioEmprestimos.getProximoId();
            Emprestimo emprestimo = new Emprestimo(id, usuario, livro, new Date());

            usuario.addEmprestimo(emprestimo);
            livro.retirarUmDisponivel();

            repositorioEmprestimos.salvar(emprestimo);
        }
    }

    public void realizarDevolucao(int id) {
        if(!repositorioEmprestimos.contemId(id)) return;

        Emprestimo emprestimo = repositorioEmprestimos.buscar(id);

        if(emprestimo.isDevolvido()) return;

        emprestimo.registrarDevolucao(new Date());

        emprestimo.getUsuario().removeEmprestimo(emprestimo);
        emprestimo.getLivro().adicionarUmDisponivel();
    }
}

import domain.Docente;
import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;
import repository.RepositorioEmprestimos;
import services.ServicoEmprestimos;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Usuario nay = new Docente("123456789", "nay1", "DDN", TitulacaoAcademica.MESTRE);
        Livro abc = new Livro(12, "abc", "nay2", "cba", 2);

        RepositorioEmprestimos repositorioEmprestimos = new RepositorioEmprestimos();
        ServicoEmprestimos servicoEmprestimos = new ServicoEmprestimos(repositorioEmprestimos);

        servicoEmprestimos.realizarEmprestimo(nay, abc);

        System.out.println(abc.getQuantidadeDisponivel());
        System.out.println(nay.getEmprestimos().size());

        servicoEmprestimos.realizarDevolucao(0);

        System.out.println(abc.getQuantidadeDisponivel());
        System.out.println(nay.getEmprestimos().size());
    }
}

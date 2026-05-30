import domain.Docente;
import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;

public class Test {
    public static void main(String[] args) {
        Usuario marcio = new Docente("11274066638", "momo", "DDN", TitulacaoAcademica.MESTRE);
        Livro abc = new Livro(12, "abc", "nay", "cba", 2);
        Emprestimo emprestimo1 = new Emprestimo(1, marcio, abc);
        Emprestimo emprestimo2 = new Emprestimo(1, marcio, abc);

        System.out.println(emprestimo1.equals(emprestimo2));
    }
}

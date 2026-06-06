import domain.Docente;
import domain.Livro;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;
import repository.RepositorioEmprestimos;
import repository.RepositorioLivros;
import repository.RepositorioUsuarios;
import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;

public class Test {
    public static void main(String[] args) {
        RepositorioLivros repositorioLivros = new RepositorioLivros();
        ServicoLivros servicoLivros = new ServicoLivros(repositorioLivros);
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios();
        ServicoUsuarios servicoUsuarios = new ServicoUsuarios(repositorioUsuarios);

        servicoLivros.cadastrarLivro("A casa de hades", "Rick riordan", "Paradidatico", 4);
        System.out.println(repositorioLivros.buscar(0).getTitulo());

        servicoLivros.editarLivro(0, "A casa de Hades", "Rick riordan", "Paradidatico");
        System.out.println(repositorioLivros.buscar(0).getTitulo());

        servicoUsuarios.cadastrarAluno("123.456.789-12", "kira", "202610509");
        System.out.println(repositorioUsuarios.buscarUsuario("123.456.789-12").getNome());

        servicoUsuarios.editarAluno("123.456.789-12", "Kira", "202610509");
        System.out.println(repositorioUsuarios.buscarUsuario("123.456.789-12").getNome());


    }
}

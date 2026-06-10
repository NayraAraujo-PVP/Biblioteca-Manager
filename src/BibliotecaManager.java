import datamanager.DataManager;
import datamanager.JsonFileDataManager;
import repository.RepositorioEmprestimos;
import repository.RepositorioLivros;
import repository.RepositorioUsuarios;
import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;
import ui.UserInterface;

public class BibliotecaManager {
    public static void main(String[] args) {
        DataManager dataManager = new JsonFileDataManager();

        RepositorioLivros repositorioLivros = new RepositorioLivros(dataManager);
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(dataManager);
        RepositorioEmprestimos repositorioEmprestimos = new RepositorioEmprestimos(repositorioLivros, repositorioUsuarios, dataManager);

        ServicoLivros servicoLivros = new ServicoLivros(repositorioLivros);
        ServicoUsuarios servicoUsuarios = new ServicoUsuarios(repositorioUsuarios);
        ServicoEmprestimos servicoEmprestimos = new ServicoEmprestimos(repositorioEmprestimos);

        UserInterface userInterface = new UserInterface();
        userInterface.setup(servicoEmprestimos, servicoLivros, servicoUsuarios);
        userInterface.start();
    }
}

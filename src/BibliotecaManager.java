import datamanager.DataManager;
import datamanager.JsonFileDataManager;
import repository.RepositorioEmprestimos;
import repository.RepositorioLivros;
import repository.RepositorioUsuarios;
import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;
import ui.UserInterface;

/**
 * Classe principal de entrada do sistema de gerenciamento de biblioteca.
 * Responsável por realizar a composição dos objetos, instanciando repositórios,
 * serviços e a interface do usuário, além de iniciar o ciclo de vida da aplicação.
 */
public class BibliotecaManager {

    /**
     * Método principal que inicializa a infraestrutura de dados, as camadas de 
     * serviço e a interface do sistema.
     *
     * @param args argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        // Inicializa o gerenciador de persistência de dados
        DataManager dataManager = new JsonFileDataManager();

        // Inicializa os repositórios
        RepositorioLivros repositorioLivros = new RepositorioLivros(dataManager);
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(dataManager);
        RepositorioEmprestimos repositorioEmprestimos = new RepositorioEmprestimos(repositorioLivros, repositorioUsuarios, dataManager);

        // Inicializa as camadas de serviço com as respectivas dependências de repositório
        ServicoLivros servicoLivros = new ServicoLivros(repositorioLivros);
        ServicoUsuarios servicoUsuarios = new ServicoUsuarios(repositorioUsuarios);
        ServicoEmprestimos servicoEmprestimos = new ServicoEmprestimos(repositorioEmprestimos);

        // Configura e inicia a interface do usuário
        UserInterface userInterface = new UserInterface();
        userInterface.setup(servicoEmprestimos, servicoLivros, servicoUsuarios);
        userInterface.start();
    }
}
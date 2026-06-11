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
 * Classe principal responsável por inicializar o sistema de gerenciamento da biblioteca.
 * Ela atua como o ponto de entrada da aplicação, configurando a injeção de dependências
 * entre o gerenciamento de dados, repositórios, serviços e a interface com o usuário.
 */
public class BibliotecaManager {
    
    /**
     * Método principal que executa a aplicação.
     * Ele realiza a instanciação do gerenciador de dados em JSON, configura os repositórios 
     * e os serviços da regra de negócio, e por fim inicia a interface gráfica ou de terminal.
     * * @param args Argumentos de linha de comando (não utilizados nesta aplicação).
     */
    public static void main(String[] args) {
        
        // Inicializa o gerenciador de dados configurado para utilizar arquivos JSON
        DataManager dataManager = new JsonFileDataManager();

        // Instancia os repositórios injetando a dependência do gerenciador de dados
        RepositorioLivros repositorioLivros = new RepositorioLivros(dataManager);
        RepositorioUsuarios repositorioUsuarios = new RepositorioUsuarios(dataManager);
        RepositorioEmprestimos repositorioEmprestimos = new RepositorioEmprestimos(repositorioLivros, repositorioUsuarios, dataManager);

        // Instancia os serviços injetando os repositórios correspondentes
        ServicoLivros servicoLivros = new ServicoLivros(repositorioLivros);
        ServicoUsuarios servicoUsuarios = new ServicoUsuarios(repositorioUsuarios);
        ServicoEmprestimos servicoEmprestimos = new ServicoEmprestimos(repositorioEmprestimos);

        // Configura a interface de usuário com os serviços necessários e inicia a aplicação
        UserInterface userInterface = new UserInterface();
        userInterface.setup(servicoEmprestimos, servicoLivros, servicoUsuarios);
        userInterface.start();
    }
}
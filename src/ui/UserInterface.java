package ui;

import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;
import ui.screen.impl.*;

import java.util.Scanner;

/**
 * Classe responsável por gerenciar a interface de linha de comando (terminal) da aplicação.
 * Ela configura as telas do sistema, realiza a injeção dos serviços necessários em cada uma delas 
 * e inicia o ciclo de execução do programa.
 */
public class UserInterface {
    private final TelaManager telaManager = new TelaManager();

    /**
     * Configura os componentes da interface antes da execução principal. 
     * Este método inicializa o leitor de entrada (Scanner), instancia todas as telas disponíveis, 
     * registra cada uma no gerenciador de telas e define o Menu Principal como a tela inicial.
     * * @param servicoEmprestimos Serviço responsável pela lógica de empréstimos e devoluções.
     * @param servicoLivros      Serviço responsável pelo gerenciamento do acervo de livros.
     * @param servicoUsuarios    Serviço responsável pelo cadastro e consulta de usuários.
     */
    public void setup(ServicoEmprestimos servicoEmprestimos, ServicoLivros servicoLivros, ServicoUsuarios servicoUsuarios) {
        Scanner input = new Scanner(System.in);

        TelaMenuPrincipal telaMenuPrincipal = new TelaMenuPrincipal(telaManager, input);
        telaManager.registrarTela(TelaEnum.MENU_PRINCIPAL, telaMenuPrincipal);

        TelaCadastrarLivro telaCadastrarLivro = new TelaCadastrarLivro(telaManager, input, servicoLivros);
        telaManager.registrarTela(TelaEnum.CADASTRAR_LIVRO, telaCadastrarLivro);

        TelaPesquisarLivro telaPesquisarLivro = new TelaPesquisarLivro(telaManager, input, servicoLivros);
        telaManager.registrarTela(TelaEnum.PESQUISAR_LIVRO, telaPesquisarLivro);

        TelaCadastrarUsuario telaCadastrarUsuario = new TelaCadastrarUsuario(telaManager, input, servicoUsuarios);
        telaManager.registrarTela(TelaEnum.CADASTRAR_USUARIO, telaCadastrarUsuario);

        TelaPesquisarUsuario telaPesquisarUsuario = new TelaPesquisarUsuario(telaManager, input, servicoUsuarios);
        telaManager.registrarTela(TelaEnum.PESQUISAR_USUARIO, telaPesquisarUsuario);

        TelaRegistroDeEmprestimo telaRegistroDeEmprestimo = new TelaRegistroDeEmprestimo(telaManager, input, servicoEmprestimos, servicoLivros, servicoUsuarios);
        telaManager.registrarTela(TelaEnum.REGISTRO_DE_EMPRESTIMO, telaRegistroDeEmprestimo);

        TelaRegistrarDevolucao telaRegistrarDevolucao = new TelaRegistrarDevolucao(telaManager, input, servicoUsuarios, servicoEmprestimos);
        telaManager.registrarTela(TelaEnum.REGISTRO_DE_DEVOLUCAO, telaRegistrarDevolucao);

        telaManager.trocarTela(TelaEnum.MENU_PRINCIPAL);
    }

    /**
     * Inicia o loop de execução principal da interface. 
     * O método mantém o programa em execução contínua, acionando o gerenciador para 
     * exibir e processar a tela que estiver ativa no momento.
     */
    public void start() {
        while (true) {
            telaManager.abrirTelaAtual();
        }
    }
}
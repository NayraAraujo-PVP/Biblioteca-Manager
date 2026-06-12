package ui;

import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;
import ui.screen.impl.*;

import java.util.Scanner;

/**
 * Classe responsável pela configuração e inicialização da interface do usuário.
 * Realiza o registro de todas as telas no {@link TelaManager} e gerencia o loop
 * principal de execução da aplicação.
 */
public class UserInterface {
    
    /**
     * Gerenciador central de navegação entre as telas.
     */
    private final TelaManager telaManager = new TelaManager();

    /**
     * Configura o ambiente da interface, instanciando as telas e registrando-as 
     * no gerenciador de telas.
     *
     * @param servicoEmprestimos serviço de negócios para operações de empréstimos.
     * @param servicoLivros      serviço de negócios para operações de acervo.
     * @param servicoUsuarios    serviço de negócios para operações de usuários.
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

        TelaConsultaEmprestados telaConsultaEmprestados = new TelaConsultaEmprestados(telaManager, input, servicoEmprestimos);
        telaManager.registrarTela(TelaEnum.CONSULTA_EMPRESTADOS, telaConsultaEmprestados);

        TelaConsultaDevolucao telaConsultaDevolucao = new TelaConsultaDevolucao(telaManager, input, servicoEmprestimos);
        telaManager.registrarTela(TelaEnum.CONSULTA_DEVOLVIDOS, telaConsultaDevolucao);

        TelaConsultaPorLivro telaConsultaPorLivro = new TelaConsultaPorLivro(telaManager, input, servicoLivros, servicoEmprestimos);
        telaManager.registrarTela(TelaEnum.CONSULTA_POR_LIVRO, telaConsultaPorLivro);

        TelaConsultaHistoricoUsuario telaConsultaHistoricoUsuario = new TelaConsultaHistoricoUsuario(telaManager, input, servicoEmprestimos, servicoUsuarios);
        telaManager.registrarTela(TelaEnum.CONSULTA_HISTORICO_USUARIO, telaConsultaHistoricoUsuario);

        telaManager.trocarTela(TelaEnum.MENU_PRINCIPAL);
    }

    /**
     * Inicia o loop principal da aplicação, mantendo o sistema em execução
     * até que o usuário encerre o programa.
     */
    public void start() {
        while (true) {
            telaManager.abrirTelaAtual();
        }
    }
}
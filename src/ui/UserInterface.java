package ui;

import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;
import ui.screen.impl.TelaCadastrarLivro;
import ui.screen.impl.TelaMenuPrincipal;
import ui.screen.impl.TelaPesquisarLivro;

import java.util.Scanner;

public class UserInterface {
    private final TelaManager telaManager = new TelaManager();

    public void setup(ServicoEmprestimos servicoEmprestimos, ServicoLivros servicoLivros, ServicoUsuarios servicoUsuarios) {
        Scanner input = new Scanner(System.in);

        TelaMenuPrincipal telaMenuPrincipal = new TelaMenuPrincipal(telaManager, input);
        telaManager.registrarTela(TelaEnum.MENU_PRINCIPAL, telaMenuPrincipal);

        TelaCadastrarLivro telaCadastrarLivro = new TelaCadastrarLivro(telaManager, input, servicoLivros);
        telaManager.registrarTela(TelaEnum.CADASTRAR_LIVRO, telaCadastrarLivro);

        TelaPesquisarLivro telaPesquisarLivro = new TelaPesquisarLivro(telaManager, input, servicoLivros);
        telaManager.registrarTela(TelaEnum.PESQUISAR_LIVRO, telaPesquisarLivro);
    }

    public void start() {
        telaManager.trocarTela(TelaEnum.MENU_PRINCIPAL);
    }
}

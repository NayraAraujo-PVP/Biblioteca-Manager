package ui;

import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;
import ui.screen.TelaEnum;
import ui.screen.impl.TelaCadastrarLivro;
import ui.screen.impl.TelaMenuPrincipal;

public class UserInterface {
    private final TelaManager telaManager = new TelaManager();

    public void setup(ServicoEmprestimos servicoEmprestimos, ServicoLivros servicoLivros, ServicoUsuarios servicoUsuarios) {
        TelaMenuPrincipal telaMenuPrincipal = new TelaMenuPrincipal(telaManager);
        telaManager.registrarTela(TelaEnum.MENU_PRINCIPAL, telaMenuPrincipal);

        TelaCadastrarLivro telaCadastrarLivro = new TelaCadastrarLivro(telaManager, servicoLivros);
        telaManager.registrarTela(TelaEnum.CADASTRAR_LIVRO, telaCadastrarLivro);
    }

    public void start() {
        telaManager.trocarTela(TelaEnum.MENU_PRINCIPAL);
    }
}

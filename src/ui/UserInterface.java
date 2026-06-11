package ui;

import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;
import ui.screen.impl.*;

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

    public void start() {
        while (true) {
            telaManager.abrirTelaAtual();
        }
    }
}

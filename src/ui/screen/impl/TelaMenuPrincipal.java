package ui.screen.impl;

import java.util.Scanner;
import ui.components.ComponenteEscolha;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;

public class TelaMenuPrincipal extends Tela {
    public TelaMenuPrincipal(TelaManager telaManager, Scanner input) {
        super(telaManager, input);
    }

    @Override
    protected void executar() {
        System.out.println("MENU PRINCIPAL");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Registrar empréstimo", this::registrarEmprestimo);
        componenteEscolha.registrarOpcao("Registrar devolução", this::registrarDevolucao);
        componenteEscolha.registrarOpcao("Acervo", this::acervo);
        componenteEscolha.registrarOpcao("Usuários", this::usuarios);
        componenteEscolha.registrarOpcao("Histórico", this::historico);

        componenteEscolha.mostrarOpcoes();
    }

    private void registrarEmprestimo() {
        trocarTela(TelaEnum.REGISTRO_DE_EMPRESTIMO);
    }

    private void registrarDevolucao() {
        trocarTela(TelaEnum.REGISTRO_DE_DEVOLUCAO);
    }

    private void acervo() {
        System.out.println("ACERVO");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Cadastrar livro", this::cadastrarLivro);
        componenteEscolha.registrarOpcao("Pesquisar livro", this::pesquisarLivro);
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    private void cadastrarLivro() {
        trocarTela(TelaEnum.CADASTRAR_LIVRO);
    }

    private void pesquisarLivro() {
        trocarTela(TelaEnum.PESQUISAR_LIVRO);
    }

    private void usuarios() {
        System.out.println("USUÁRIOS");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Cadastrar usuário", this::cadastrarUsuario);
        componenteEscolha.registrarOpcao("Pesquisar usuário", this::pesquisarUsuario);
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    private void cadastrarUsuario() {
        trocarTela(TelaEnum.CADASTRAR_USUARIO);
    }

    private void pesquisarUsuario() {
        trocarTela(TelaEnum.PESQUISAR_USUARIO);
    }

    private void consultas() {
        System.out.println("CONSULTAS");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Emprestados", this::consultarEmprestados);
        componenteEscolha.registrarOpcao("Devolvidos", this::consultarDevolvidos);
        componenteEscolha.registrarOpcao("Por livro", this::consultarPorLivros);
        componenteEscolha.registrarOpcao("Histórico por usuário", this::consultarHistoricoUsuario);
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    private void consultarEmprestados() {
        trocarTela(TelaEnum.CONSULTA_EMPRESTADOS);
    }

    private void consultarDevolvidos() {
        trocarTela(TelaEnum.CONSULTA_DEVOLVIDOS);
    private void historico() {
    }

    private void consultarPorLivros() {
        trocarTela(TelaEnum.CONSULTA_POR_LIVRO);
    }

    private void consultarHistoricoUsuario() {
        trocarTela(TelaEnum.CONSULTA_HISTORICO_USUARIO);
    }
}

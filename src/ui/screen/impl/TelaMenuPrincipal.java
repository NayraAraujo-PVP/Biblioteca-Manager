package ui.screen.impl;

import java.util.Scanner;
import ui.components.ComponenteEscolha;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;

/**
 * Tela principal do sistema.
 * Responsável por apresentar o menu inicial e
 * direcionar o usuário para as demais funcionalidades.
 */
public class TelaMenuPrincipal extends Tela {

    /**
     * Constrói a tela principal.
     *
     * @param telaManager gerenciador de telas do sistema.
     * @param input leitor de entradas do usuário.
     */
    public TelaMenuPrincipal(TelaManager telaManager, Scanner input) {
        super(telaManager, input);
    }

    /**
     * Exibe as opções do menu principal.
     */
    @Override
    protected void executar() {
        System.out.println("MENU PRINCIPAL");

        // Componente responsável por exibir as opções do menu
        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        // Registro das opções disponíveis
        componenteEscolha.registrarOpcao("Registrar empréstimo", this::registrarEmprestimo);
        componenteEscolha.registrarOpcao("Registrar devolução", this::registrarDevolucao);
        componenteEscolha.registrarOpcao("Acervo", this::acervo);
        componenteEscolha.registrarOpcao("Usuários", this::usuarios);
        componenteEscolha.registrarOpcao("Histórico", this::historico);

        // Exibe as opções e aguarda a escolha do usuário
        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Abre a tela de registro de empréstimos.
     */
    private void registrarEmprestimo() {
        trocarTela(TelaEnum.REGISTRO_DE_EMPRESTIMO);
    }

    /**
     * Abre a tela de registro de devoluções.
     */
    private void registrarDevolucao() {
        trocarTela(TelaEnum.REGISTRO_DE_DEVOLUCAO);
    }

    /**
     * Exibe o submenu de gerenciamento do acervo.
     */
    private void acervo() {
        System.out.println("ACERVO");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        // Opções relacionadas ao acervo de livros
        componenteEscolha.registrarOpcao("Cadastrar livro", this::cadastrarLivro);
        componenteEscolha.registrarOpcao("Pesquisar livro", this::pesquisarLivro);
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Abre a tela de cadastro de livros.
     */
    private void cadastrarLivro() {
        trocarTela(TelaEnum.CADASTRAR_LIVRO);
    }

    /**
     * Abre a tela de pesquisa de livros.
     */
    private void pesquisarLivro() {
        trocarTela(TelaEnum.PESQUISAR_LIVRO);
    }

    /**
     * Exibe o submenu de gerenciamento de usuários.
     */
    private void usuarios() {
        System.out.println("USUÁRIOS");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        // Opções relacionadas aos usuários do sistema
        componenteEscolha.registrarOpcao("Cadastrar usuário", this::cadastrarUsuario);
        componenteEscolha.registrarOpcao("Pesquisar usuário", this::pesquisarUsuario);
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Abre a tela de cadastro de usuários.
     */
    private void cadastrarUsuario() {
        trocarTela(TelaEnum.CADASTRAR_USUARIO);
    }

    /**
     * Abre a tela de pesquisa de usuários.
     */
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
    /**
     * Exibe o histórico do sistema.
     */
    private void historico() {
    }

    private void consultarPorLivros() {
        trocarTela(TelaEnum.CONSULTA_POR_LIVRO);
    }

    private void consultarHistoricoUsuario() {
        trocarTela(TelaEnum.CONSULTA_HISTORICO_USUARIO);
    }
}

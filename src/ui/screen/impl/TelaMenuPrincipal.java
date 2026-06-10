package ui.screen.impl;

import ui.TelaManager;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.screen.components.ComponenteEscolha;

public class TelaMenuPrincipal extends Tela {
    public TelaMenuPrincipal(TelaManager telaManager) {
        super(telaManager);
    }

    @Override
    protected void limpar() {

    }

    @Override
    protected void executar() {
        System.out.println("MENU PRINCIPAL");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha();

        componenteEscolha.registrarOpcao("Registrar empréstimo", this::registrarEmprestimo);
        componenteEscolha.registrarOpcao("Registrar devolução", this::registrarDevolucao);
        componenteEscolha.registrarOpcao("Acervo", this::acervo);
        componenteEscolha.registrarOpcao("Usuários", this::usuarios);
        componenteEscolha.registrarOpcao("Histórico", this::historico);

        componenteEscolha.mostrarOpcoes();
    }

    private void registrarEmprestimo() {
    }

    private void registrarDevolucao() {
    }

    private void acervo() {
        System.out.println("ACERVO");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha();

        componenteEscolha.registrarOpcao("Cadastrar livro", this::cadastrarLivro);
        componenteEscolha.registrarOpcao("Pesquisar livro", this::pesquisarLivro);

        componenteEscolha.mostrarOpcoes();
    }

    private void cadastrarLivro() {
        trocarTela(TelaEnum.CADASTRAR_LIVRO);
    }

    private void pesquisarLivro() {
        trocarTela(TelaEnum.PESQUISAR_LIVRO);
    }

    private void usuarios() {
    }

    private void historico() {
    }
}

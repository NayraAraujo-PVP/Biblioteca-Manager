package ui.screen.impl;

import services.ServicoLivros;
import ui.screen.Tela;
import ui.screen.TelaManager;
import ui.components.ComponenteInputNumero;

import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Tela responsável pelo cadastro de novos livros
 * no sistema da biblioteca.
 */
public class TelaCadastrarLivro extends Tela {

    private final ServicoLivros servicoLivros;

    /**
     * Cria a tela de cadastro de livros.
     *
     * @param telaManager gerenciador de telas.
     * @param input scanner utilizado para entrada de dados.
     * @param servicoLivros serviço responsável pelas operações com livros.
     */
    public TelaCadastrarLivro(TelaManager telaManager,
                              Scanner input,
                              ServicoLivros servicoLivros) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
    }

    /**
     * Exibe o formulário de cadastro de livro,
     * coleta os dados informados pelo usuário e
     * realiza o cadastro no sistema.
     */
    @Override
    protected void executar() {
        System.out.println("CADASTRAR LIVRO");
        quebraLinha();

        System.out.print("Título: ");
        String titulo = input.nextLine();

        System.out.print("Autor: ");
        String autor = input.nextLine();

        System.out.print("Categoria: ");
        String categoria = input.nextLine();

        System.out.print("Quantidade total: ");
        ComponenteInputNumero componenteInputNumero =
                new ComponenteInputNumero(input);

        int quantidadeTotal = componenteInputNumero.receberNumero();

        servicoLivros.cadastrarLivro(
                titulo,
                autor,
                categoria,
                quantidadeTotal
        );

        quebraLinha();
        System.out.println("Livro cadastrado com sucesso!");
        quebraLinha();

        voltarMenu();
    }
}

package ui.screen.impl;

import services.ServicoLivros;
import ui.screen.TelaManager;
import ui.screen.Tela;
import ui.components.ComponenteInputNumero;

import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

public class TelaCadastrarLivro extends Tela {
    private final ServicoLivros servicoLivros;

    public TelaCadastrarLivro(TelaManager telaManager, Scanner input, ServicoLivros servicoLivros) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
    }

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
        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        int quantidadeTotal = componenteInputNumero.receberNumero();

        servicoLivros.cadastrarLivro(titulo, autor, categoria, quantidadeTotal);

        quebraLinha();
        System.out.println("Livro cadastrado com sucesso!");
        quebraLinha();

        voltarMenu();
    }
}

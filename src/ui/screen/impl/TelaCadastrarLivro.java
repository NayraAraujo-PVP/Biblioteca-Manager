package ui.screen.impl;

import services.ServicoLivros;
import ui.screen.TelaManager;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.components.ComponenteInputNumero;

import java.util.Scanner;

public class TelaCadastrarLivro extends Tela {
    private final ServicoLivros servicoLivros;

    public TelaCadastrarLivro(TelaManager telaManager, Scanner input, ServicoLivros servicoLivros) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
    }

    @Override
    protected void executar() {
        System.out.println("CADASTRAR LIVRO");
        System.out.println();

        System.out.print("Título: ");
        String titulo = input.next();

        System.out.print("Autor: ");
        String autor = input.next();

        System.out.print("Categoria: ");
        String categoria = input.next();

        System.out.print("Quantidade total: ");
        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        int quantidadeTotal = componenteInputNumero.receberNumero();

        servicoLivros.cadastrarLivro(titulo, autor, categoria, quantidadeTotal);

        System.out.println();
        System.out.println("Livro cadastrado com sucesso!");
        System.out.println();

        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }
}

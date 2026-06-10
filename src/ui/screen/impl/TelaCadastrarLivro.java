package ui.screen.impl;

import services.ServicoLivros;
import ui.TelaManager;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.screen.components.ComponenteInputNumero;

import java.util.Scanner;

public class TelaCadastrarLivro extends Tela {
    private String titulo;
    private String autor;
    private String categoria;
    private int quantidadeTotal;

    private final ServicoLivros servicoLivros;

    public TelaCadastrarLivro(TelaManager telaManager, ServicoLivros servicoLivros) {
        super(telaManager);
        this.servicoLivros = servicoLivros;
    }

    @Override
    protected void limpar() {

    }

    @Override
    protected void executar() {
        Scanner input = new Scanner(System.in);

        System.out.println("CADASTRAR LIVRO");
        System.out.println();

        System.out.print("Título: ");
        titulo = input.next();

        System.out.print("Autor: ");
        autor = input.next();

        System.out.print("Categoria: ");
        categoria = input.next();

        System.out.print("Quantidade total: ");
        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero();
        quantidadeTotal = componenteInputNumero.receberNumero();

        servicoLivros.cadastrarLivro(titulo, autor, categoria, quantidadeTotal);

        System.out.println();
        System.out.println("Livro cadastrado com sucesso!");
        System.out.println();

        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }
}

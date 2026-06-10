package ui.screen.impl;

import domain.Livro;
import services.ServicoLivros;
import ui.components.ComponenteVisualizacaoBlocos;
import ui.screen.Tela;
import ui.screen.TelaManager;

import java.util.List;
import java.util.Scanner;

public class TelaPesquisarLivro extends Tela {
    private final ServicoLivros servicoLivros;

    public TelaPesquisarLivro(TelaManager telaManager, Scanner input, ServicoLivros servicoLivros) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
    }

    @Override
    protected void executar() {
        System.out.println("PESQUISAR LIVRO");
        System.out.println();

        iniciarPesquisa();
    }

    private void iniciarPesquisa() {
        System.out.print("Insira seu termo de pesquisa (titulo, autor ou categoria): ");

        String termoPesquisa = input.next();

        List<Livro> livros = servicoLivros.buscar(termoPesquisa);

        ComponenteVisualizacaoBlocos componenteVisualizacaoBlocos = new ComponenteVisualizacaoBlocos(
                List.of(
                        "TÍTULO",
                        "AUTOR",
                        "CATEGORIA",
                        "QUANTIDADE"));

        for (Livro livro : livros) {
            componenteVisualizacaoBlocos.adicionarItem(
                    livro.getId(),
                    List.of(
                            livro.getTitulo(),
                            livro.getAutor(),
                            livro.getCategoria(),
                            String.format("%d / %d", livro.getQuantidadeTotal() - livro.getQuantidadeEmprestada(), livro.getQuantidadeTotal())));
        }

        componenteVisualizacaoBlocos.mostrar();

        // TODO: terminar o fluxo do acervo
    }
}

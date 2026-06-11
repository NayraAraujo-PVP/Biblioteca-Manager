package ui.screen.impl;

import domain.Livro;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import services.ServicoLivros;
import static ui.UIUtils.quebraLinha;
import ui.components.ComponenteEscolha;
import ui.components.ComponenteInputNumero;
import ui.components.ComponenteVisualizacaoBlocos;
import ui.screen.Tela;
import ui.screen.TelaManager;

public class TelaPesquisarLivro extends Tela {
    private final ServicoLivros servicoLivros;

    public TelaPesquisarLivro(TelaManager telaManager, Scanner input, ServicoLivros servicoLivros) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
    }

    @Override
    protected void executar() {
        System.out.println("PESQUISAR LIVRO");
        quebraLinha();

        iniciarPesquisa();
    }

    private void iniciarPesquisa() {
        System.out.print("Insira seu termo de pesquisa (titulo, autor ou categoria) ou 'voltar' para retornar ao menu: ");

        String termoPesquisa = input.nextLine();
        if (termoPesquisa.toLowerCase().equals("voltar")) {
            voltarMenu();
            return;
        }

        List<Livro> livros = servicoLivros.buscar(termoPesquisa);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado!");
            quebraLinha();
            iniciarPesquisa();
        }

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

        iniciarSelecaoLivro(livros);
    }

    private void iniciarSelecaoLivro(List<Livro> livros) {
        quebraLinha();
        System.out.println("Digite o ID do livro desejado ou 'voltar' para pesquisar novamente");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int escolha = componenteInputNumero.receberNumero();

        if(escolha == -1) {
            iniciarPesquisa();
            return;
        }

        Optional<Livro> livroSelecionadoOpt = livros.stream().filter(livro -> livro.getId() == escolha).findFirst();

        if(livroSelecionadoOpt.isEmpty()) {
            System.out.println("Livro não encontrado");
            iniciarSelecaoLivro(livros);

            return;
        }

        Livro livroSelecionado = livroSelecionadoOpt.get();
        apresentarOpcoesLivro(livroSelecionado);
    }

    private void apresentarOpcoesLivro(Livro livroSelecionado) {
        quebraLinha();
        System.out.printf("O livro '%s' foi selecionado %n", livroSelecionado.getTitulo());

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Editar livro", () -> editarLivro(livroSelecionado));
        componenteEscolha.registrarOpcao("Alterar quantidade", () -> alterarQuantidadeLivro(livroSelecionado));
        componenteEscolha.registrarOpcao("Remover livro", () -> removerLivro(livroSelecionado));
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    private void editarLivro(Livro livroSelecionado) {
        System.out.print("Novo título [" + livroSelecionado.getTitulo() + "]: ");
        String novoTitulo = input.nextLine();
        if(novoTitulo.trim().isEmpty()) novoTitulo = livroSelecionado.getTitulo();

        System.out.print("Novo autor [" + livroSelecionado.getAutor() + "]: ");
        String novoAutor = input.nextLine();
        if(novoAutor.trim().isEmpty()) novoAutor = livroSelecionado.getAutor();

        System.out.print("Nova categoria [" + livroSelecionado.getCategoria() + "]: ");
        String novaCategoria = input.nextLine();
        if(novaCategoria.trim().isEmpty()) novaCategoria = livroSelecionado.getCategoria();

        quebraLinha();

        servicoLivros.editarLivro(livroSelecionado.getId(), novoTitulo, novoAutor, novaCategoria);
        voltarMenu();
    }

    private void alterarQuantidadeLivro(Livro livroSelecionado) {
        System.out.println("Digite a nova quantidade total deste livro:");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        int novaQuantidade = componenteInputNumero.receberNumero();

        if(livroSelecionado.getQuantidadeEmprestada() > novaQuantidade) {
            System.out.println("Impossível a alteração");
            System.out.println("Motivo: há mais unidades emprestadas do que a quantidade inserida.");

            apresentarOpcoesLivro(livroSelecionado);
            return;
        }

        servicoLivros.alteraQuantidadeTotal(livroSelecionado.getId(), novaQuantidade);
        voltarMenu();
    }

    private void removerLivro(Livro livroSelecionado) {
        if(livroSelecionado.getQuantidadeEmprestada() > 0) {
            System.out.println("Impossível a remoção");
            System.out.println("Motivo: há unidades emprestadas.");

            apresentarOpcoesLivro(livroSelecionado);
            return;
        }

        servicoLivros.alteraQuantidadeTotal(livroSelecionado.getId(), 0);
        voltarMenu();
    }
}

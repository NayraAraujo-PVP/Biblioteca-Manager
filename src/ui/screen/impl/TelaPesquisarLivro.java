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

/**
 * Tela responsável pela pesquisa e gerenciamento de livros.
 * Permite buscar, editar, alterar quantidade e remover livros.
 */
public class TelaPesquisarLivro extends Tela {

    private final ServicoLivros servicoLivros;

    /**
     * Cria a tela de pesquisa de livros.
     *
     * @param telaManager gerenciador de telas.
     * @param input leitor de entradas do usuário.
     * @param servicoLivros serviço responsável pelas operações de livros.
     */
    public TelaPesquisarLivro(TelaManager telaManager, Scanner input, ServicoLivros servicoLivros) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
    }

    /**
     * Exibe a tela de pesquisa de livros.
     */
    @Override
    protected void executar() {
        System.out.println("PESQUISAR LIVRO");
        quebraLinha();

        iniciarPesquisa();
    }

    /**
     * Solicita um termo de pesquisa e exibe os resultados encontrados.
     */
    private void iniciarPesquisa() {
        System.out.print("Insira seu termo de pesquisa (titulo, autor ou categoria) ou 'voltar' para retornar ao menu: ");

        String termoPesquisa = input.nextLine();

        // Retorna ao menu principal caso o usuário deseje sair da pesquisa.
        if (termoPesquisa.toLowerCase().equals("voltar")) {
            voltarMenu();
            return;
        }

        // Busca livros que correspondam ao termo informado.
        List<Livro> livros = servicoLivros.buscar(termoPesquisa);

        // Caso nenhum livro seja encontrado, reinicia a pesquisa.
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado!");
            quebraLinha();
            iniciarPesquisa();
        }

        // Componente utilizado para exibir os resultados encontrados.
        ComponenteVisualizacaoBlocos componenteVisualizacaoBlocos = new ComponenteVisualizacaoBlocos(
                List.of(
                        "TÍTULO",
                        "AUTOR",
                        "CATEGORIA",
                        "QUANTIDADE"));

        // Adiciona cada livro encontrado à visualização.
        for (Livro livro : livros) {
            componenteVisualizacaoBlocos.adicionarItem(
                    livro.getId(),
                    List.of(
                            livro.getTitulo(),
                            livro.getAutor(),
                            livro.getCategoria(),
                            String.format("%d / %d",
                                    livro.getQuantidadeTotal() - livro.getQuantidadeEmprestada(),
                                    livro.getQuantidadeTotal())));
        }

        componenteVisualizacaoBlocos.mostrar();

        iniciarSelecaoLivro(livros);
    }

    /**
     * Permite ao usuário selecionar um livro pelo ID.
     *
     * @param livros lista de livros encontrados.
     */
    private void iniciarSelecaoLivro(List<Livro> livros) {
        quebraLinha();
        System.out.println("Digite o ID do livro desejado ou 'voltar' para pesquisar novamente");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);

        // Permite utilizar a palavra "voltar" como alternativa ao ID.
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int escolha = componenteInputNumero.receberNumero();

        // Retorna para uma nova pesquisa.
        if(escolha == -1) {
            iniciarPesquisa();
            return;
        }

        // Procura o livro correspondente ao ID informado.
        Optional<Livro> livroSelecionadoOpt = livros.stream()
                .filter(livro -> livro.getId() == escolha)
                .findFirst();

        // Caso o ID não exista na lista exibida.
        if(livroSelecionadoOpt.isEmpty()) {
            System.out.println("Livro não encontrado");
            iniciarSelecaoLivro(livros);

            return;
        }

        Livro livroSelecionado = livroSelecionadoOpt.get();
        apresentarOpcoesLivro(livroSelecionado);
    }

    /**
     * Exibe as opções disponíveis para o livro selecionado.
     *
     * @param livroSelecionado livro escolhido pelo usuário.
     */
    private void apresentarOpcoesLivro(Livro livroSelecionado) {
        quebraLinha();
        System.out.printf("O livro '%s' foi selecionado %n", livroSelecionado.getTitulo());

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        // Registra as ações disponíveis para o livro.
        componenteEscolha.registrarOpcao("Editar livro", () -> editarLivro(livroSelecionado));
        componenteEscolha.registrarOpcao("Alterar quantidade", () -> alterarQuantidadeLivro(livroSelecionado));
        componenteEscolha.registrarOpcao("Remover livro", () -> removerLivro(livroSelecionado));
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Permite editar as informações de um livro.
     *
     * @param livroSelecionado livro que será editado.
     */
    private void editarLivro(Livro livroSelecionado) {
        System.out.print("Novo título [" + livroSelecionado.getTitulo() + "]: ");
        String novoTitulo = input.nextLine();

        // Mantém o título atual caso nenhum valor seja informado.
        if(novoTitulo.trim().isEmpty())
            novoTitulo = livroSelecionado.getTitulo();

        System.out.print("Novo autor [" + livroSelecionado.getAutor() + "]: ");
        String novoAutor = input.nextLine();

        // Mantém o autor atual caso nenhum valor seja informado.
        if(novoAutor.trim().isEmpty())
            novoAutor = livroSelecionado.getAutor();

        System.out.print("Nova categoria [" + livroSelecionado.getCategoria() + "]: ");
        String novaCategoria = input.nextLine();

        // Mantém a categoria atual caso nenhum valor seja informado.
        if(novaCategoria.trim().isEmpty())
            novaCategoria = livroSelecionado.getCategoria();

        quebraLinha();

        // Salva as alterações realizadas.
        servicoLivros.editarLivro(
                livroSelecionado.getId(),
                novoTitulo,
                novoAutor,
                novaCategoria);

        voltarMenu();
    }

    /**
     * Altera a quantidade total de exemplares de um livro.
     *
     * @param livroSelecionado livro que terá sua quantidade alterada.
     */
    private void alterarQuantidadeLivro(Livro livroSelecionado) {
        System.out.println("Digite a nova quantidade total deste livro:");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        int novaQuantidade = componenteInputNumero.receberNumero();

        // Impede que a quantidade total fique menor que a quantidade emprestada.
        if(livroSelecionado.getQuantidadeEmprestada() > novaQuantidade) {
            System.out.println("Impossível a alteração");
            System.out.println("Motivo: há mais unidades emprestadas do que a quantidade inserida.");

            apresentarOpcoesLivro(livroSelecionado);
            return;
        }

        servicoLivros.alteraQuantidadeTotal(livroSelecionado.getId(), novaQuantidade);
        voltarMenu();
    }

    /**
     * Remove um livro do acervo.
     *
     * @param livroSelecionado livro que será removido.
     */
    private void removerLivro(Livro livroSelecionado) {

        // Não permite remover livros que ainda possuem empréstimos ativos.
        if(livroSelecionado.getQuantidadeEmprestada() > 0) {
            System.out.println("Impossível a remoção");
            System.out.println("Motivo: há unidades emprestadas.");

            apresentarOpcoesLivro(livroSelecionado);
            return;
        }

        // A remoção é realizada definindo a quantidade total para zero.
        servicoLivros.alteraQuantidadeTotal(livroSelecionado.getId(), 0);
        voltarMenu();
    }
}
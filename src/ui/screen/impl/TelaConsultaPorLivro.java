package ui.screen.impl;

import domain.Emprestimo;
import domain.Livro;
import services.ServicoEmprestimos;
import services.ServicoLivros;
import ui.components.ComponenteInputNumero;
import ui.components.ComponenteVisualizacaoBlocos;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Tela de interface responsável pela consulta de empréstimos ativos vinculados a um livro específico.
 * Permite ao usuário localizar um livro por seu identificador único e visualizar uma lista
 * detalhada dos empréstimos pendentes associados a ele, utilizando o {@link ComponenteVisualizacaoBlocos}.
 */
public class TelaConsultaPorLivro extends Tela {

    private final ServicoLivros servicoLivros;
    private final ServicoEmprestimos servicoEmprestimos;

    /**
     * Constrói a tela de consulta por livro.
     *
     * @param telaManager        o gerenciador de navegação entre telas.
     * @param input              o {@link Scanner} para entrada de dados.
     * @param servicoLivros      o serviço para busca e validação de livros.
     * @param servicoEmprestimos o serviço para consultar os empréstimos associados.
     */
    public TelaConsultaPorLivro(TelaManager telaManager, Scanner input, ServicoLivros servicoLivros, ServicoEmprestimos servicoEmprestimos) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
        this.servicoEmprestimos = servicoEmprestimos;
    }

    /**
     * Executa o fluxo inicial da tela, exibindo o cabeçalho e disparando a solicitação do ID do livro.
     */
    @Override
    protected void executar() {
        System.out.println("CONSULTA POR LIVRO");
        quebraLinha();
        receberLivro();
    }

    /**
     * Solicita ao usuário o ID do livro a ser consultado. Permite o retorno ao menu principal.
     */
    private void receberLivro() {
        System.out.println("Digite o ID do livro desejado ou 'voltar' para retornar ao menu");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int escolha = componenteInputNumero.receberNumero();

        if (escolha == -1) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Livro> livroSelecionadoOpt = servicoLivros.buscarPorId(escolha);

        if (livroSelecionadoOpt.isEmpty()) {
            System.out.println("Livro não encontrado");
            receberLivro();
            return;
        }

        Livro livroSelecionado = livroSelecionadoOpt.get();
        mostrarEmprestimos(livroSelecionado);
    }

    /**
     * Recupera e exibe os empréstimos ativos para o livro selecionado.
     *
     * @param livroSelecionado o livro cujos empréstimos serão listados.
     */
    private void mostrarEmprestimos(Livro livroSelecionado) {
        List<Emprestimo> emprestimoList = servicoEmprestimos.buscaEmprestimoPorLivro(livroSelecionado);

        ComponenteVisualizacaoBlocos componenteVisualizacaoBlocos = new ComponenteVisualizacaoBlocos(
                List.of(
                        "DATA",
                        "HORÁRIO",
                        "CPF",
                        "NOME",
                        "DEVOLUÇÃO PREVISTA"
                )
        );

        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        for (Emprestimo emprestimo : emprestimoList) {
            componenteVisualizacaoBlocos.adicionarItem(emprestimo.getId(), List.of(
                    formatoData.format(emprestimo.getDataRetirada()),
                    formatoHora.format(emprestimo.getDataRetirada()),
                    emprestimo.getUsuario().getCpf(),
                    emprestimo.getUsuario().getNome(),
                    formatoData.format(emprestimo.dataLimiteDevolucao())
            ));
        }

        componenteVisualizacaoBlocos.mostrar();

        receberLivro();
    }
}
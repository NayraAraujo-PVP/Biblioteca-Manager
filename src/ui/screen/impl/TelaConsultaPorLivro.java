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

public class TelaConsultaPorLivro extends Tela {
    private final ServicoLivros servicoLivros;
    private final ServicoEmprestimos servicoEmprestimos;

    public TelaConsultaPorLivro(TelaManager telaManager, Scanner input, ServicoLivros servicoLivros, ServicoEmprestimos servicoEmprestimos) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
        this.servicoEmprestimos = servicoEmprestimos;
    }

    @Override
    protected void executar() {
        System.out.println("CONSULTA POR LIVRO");
        quebraLinha();
        receberLivro();
    }

    private void receberLivro() {
        System.out.println("Digite o ID do livro desejado ou 'voltar' para retornar ao menu");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int escolha = componenteInputNumero.receberNumero();

        if(escolha == -1) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Livro> livroSelecionadoOpt = servicoLivros.buscarPorId(escolha);

        if(livroSelecionadoOpt.isEmpty()) {
            System.out.println("Livro não encontrado");
            receberLivro();

            return;
        }

        Livro livroSelecionado = livroSelecionadoOpt.get();
        mostrarEmprestimos(livroSelecionado);
    }

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

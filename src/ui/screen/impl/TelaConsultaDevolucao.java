package ui.screen.impl;

import domain.Emprestimo;
import services.ServicoEmprestimos;
import ui.components.ComponenteInputData;
import ui.components.ComponenteVisualizacaoBlocos;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Tela de interface responsável pela consulta de livros devolvidos em datas específicas.
 * Permite ao usuário filtrar empréstimos finalizados por data e apresenta os resultados
 * de forma tabular estruturada via {@link ComponenteVisualizacaoBlocos}.
 */
public class TelaConsultaDevolucao extends Tela {

    private final ServicoEmprestimos servicoEmprestimos;

    /**
     * Constrói a tela de consulta de devoluções.
     *
     * @param telaManager        o gerenciador de navegação entre telas.
     * @param input              o {@link Scanner} para entrada de dados.
     * @param servicoEmprestimos o serviço utilizado para consultar registros de empréstimos.
     */
    public TelaConsultaDevolucao(TelaManager telaManager, Scanner input, ServicoEmprestimos servicoEmprestimos) {
        super(telaManager, input);
        this.servicoEmprestimos = servicoEmprestimos;
    }

    /**
     * Executa o fluxo inicial da tela, exibindo o cabeçalho e disparando a solicitação de data.
     */
    @Override
    protected void executar() {
        System.out.println("CONSULTAR LIVROS DEVOLVIDOS");
        quebraLinha();

        solicitaData();
    }

    /**
     * Solicita ao usuário a data da consulta, oferecendo atalhos (pseudônimos) como 'voltar' e 'hoje'.
     */
    private void solicitaData() {
        System.out.println("Digite a data da consulta ou 'voltar' para retornar ao menu");

        ComponenteInputData componenteInputData = new ComponenteInputData(input);
        componenteInputData.registrarPseudonimo("voltar", null);
        componenteInputData.registrarPseudonimo("hoje", LocalDate.now());

        LocalDate dataEscolhida = componenteInputData.receberData();

        if (dataEscolhida == null) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        buscarData(dataEscolhida);
    }

    /**
     * Busca e exibe os empréstimos devolvidos na data selecionada.
     *
     * @param dataEscolhida a data de referência para a consulta.
     */
    private void buscarData(LocalDate dataEscolhida) {
        List<Emprestimo> emprestimoList = servicoEmprestimos.buscarDataDevolucao(dataEscolhida);

        ComponenteVisualizacaoBlocos componenteVisualizacaoBlocos = new ComponenteVisualizacaoBlocos(
                List.of(
                        "HORÁRIO",
                        "CPF",
                        "NOME",
                        "ID LIVRO",
                        "TÍTULO",
                        "AUTOR"
                )
        );

        SimpleDateFormat formato = new SimpleDateFormat("HH:mm");

        for (Emprestimo emprestimo : emprestimoList) {
            componenteVisualizacaoBlocos.adicionarItem(emprestimo.getId(), List.of(
                    formato.format(emprestimo.getDataRetirada()),
                    emprestimo.getUsuario().getCpf(),
                    emprestimo.getUsuario().getNome(),
                    String.valueOf(emprestimo.getLivro().getId()),
                    emprestimo.getLivro().getTitulo(),
                    emprestimo.getLivro().getAutor()
            ));
        }

        componenteVisualizacaoBlocos.mostrar();

        solicitaData();
    }
}
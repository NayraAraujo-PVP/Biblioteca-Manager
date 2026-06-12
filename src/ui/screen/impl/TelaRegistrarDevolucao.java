package ui.screen.impl;

import domain.Emprestimo;
import domain.Usuario;
import services.ServicoEmprestimos;
import services.ServicoUsuarios;
import ui.components.ComponenteInputNumero;
import ui.components.ComponenteVisualizacaoBlocos;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Tela de interface responsável pelo fluxo de registro de devolução de livros.
 * Orquestra a identificação do usuário, a listagem de empréstimos ativos,
 * a seleção do item para devolução e o cálculo de multas, se aplicável.
 */
public class TelaRegistrarDevolucao extends Tela {

    private final ServicoUsuarios servicoUsuarios;
    private final ServicoEmprestimos servicoEmprestimos;

    /**
     * Constrói a tela de registro de devolução.
     *
     * @param telaManager        o gerenciador de navegação entre telas.
     * @param input              o {@link Scanner} para entrada de dados.
     * @param servicoUsuarios    o serviço de usuários para buscar o cliente.
     * @param servicoEmprestimos o serviço de empréstimos para processar a devolução.
     */
    public TelaRegistrarDevolucao(TelaManager telaManager, Scanner input, ServicoUsuarios servicoUsuarios, ServicoEmprestimos servicoEmprestimos) {
        super(telaManager, input);
        this.servicoUsuarios = servicoUsuarios;
        this.servicoEmprestimos = servicoEmprestimos;
    }

    /**
     * Executa o fluxo inicial da tela de devolução.
     */
    @Override
    protected void executar() {
        System.out.println("REGISTRO DE DEVOLUÇÃO");
        quebraLinha();

        solicitarUsuario();
    }

    /**
     * Solicita o CPF ou matrícula do usuário para localizar seus empréstimos ativos.
     */
    private void solicitarUsuario() {
        System.out.print("Digite o CPF ou matrícula do usuário, ou 'voltar' para retornar ao menu: ");
        String chaveDeBusca = input.nextLine();

        if (chaveDeBusca.equalsIgnoreCase("voltar")) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Usuario> usuarioOpt = servicoUsuarios.buscarUsuarioPorCpfOuMatricula(chaveDeBusca);

        if (usuarioOpt.isEmpty()) {
            System.out.println("Usuário não encontrado.");
            quebraLinha();
            solicitarUsuario();
            return;
        }

        Usuario usuario = usuarioOpt.get();
        solicitarEmprestimo(usuario);
    }

    /**
     * Exibe a lista de empréstimos ativos do usuário e aguarda a seleção para devolução.
     *
     * @param usuario o usuário do qual os empréstimos serão consultados.
     */
    private void solicitarEmprestimo(Usuario usuario) {
        List<Emprestimo> emprestimosList = servicoEmprestimos.buscarEmprestimosPara(usuario, true);

        if (emprestimosList.isEmpty()) {
            System.out.println("Esse usuário não contém empréstimos ativos!");
            quebraLinha();
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        ComponenteVisualizacaoBlocos componenteVisualizacaoBlocos = new ComponenteVisualizacaoBlocos(
                List.of(
                        "TÍTULO",
                        "AUTOR",
                        "CATEGORIA"));

        for (Emprestimo emprestimo : emprestimosList) {
            componenteVisualizacaoBlocos.adicionarItem(
                    emprestimo.getLivro().getId(),
                    List.of(
                            emprestimo.getLivro().getTitulo(),
                            emprestimo.getLivro().getAutor(),
                            emprestimo.getLivro().getCategoria()));
        }

        componenteVisualizacaoBlocos.mostrar();

        iniciarSelecaoEmprestimo(emprestimosList);
    }

    /**
     * Captura o ID do livro a ser devolvido dentre as opções ativas do usuário.
     *
     * @param emprestimosList a lista de empréstimos ativos disponíveis para devolução.
     */
    private void iniciarSelecaoEmprestimo(List<Emprestimo> emprestimosList) {
        quebraLinha();
        System.out.println("Digite o ID do livro a ser devolvido ou 'voltar' para retornar ao menu");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int escolha = componenteInputNumero.receberNumero();

        if (escolha == -1) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Emprestimo> emprestimoSelecionadoOpt = emprestimosList.stream()
                .filter(emprestimo -> emprestimo.getLivro().getId() == escolha)
                .findFirst();

        if (emprestimoSelecionadoOpt.isEmpty()) {
            System.out.println("Livro não encontrado");
            iniciarSelecaoEmprestimo(emprestimosList);
            return;
        }

        Emprestimo emprestimoSelecionado = emprestimoSelecionadoOpt.get();
        realizarDevolucao(emprestimoSelecionado.getId());
    }

    /**
     * Processa a devolução através do serviço e exibe o status, incluindo o valor
     * da multa caso o prazo tenha sido excedido.
     *
     * @param id o identificador do empréstimo a ser finalizado.
     */
    private void realizarDevolucao(int id) {
        Optional<Emprestimo> emprestimoOpt = servicoEmprestimos.realizarDevolucao(id);

        if (emprestimoOpt.isEmpty()) {
            System.out.println("Erro inesperado!");
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Emprestimo emprestimo = emprestimoOpt.get();

        System.out.println("Devolução realizada com sucesso!");

        Double valorMulta = emprestimo.getValorMulta();
        if (valorMulta != null) {
            quebraLinha();
            System.out.println("Houve um atraso na devolução.");
            System.out.printf("Valor da multa: R$ %.2f %n", valorMulta);
        }

        quebraLinha();
        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }
}
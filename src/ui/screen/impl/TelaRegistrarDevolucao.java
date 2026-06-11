package ui.screen.impl;

import domain.Emprestimo;
import domain.Livro;
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

public class TelaRegistrarDevolucao extends Tela {
    private final ServicoUsuarios servicoUsuarios;
    private final ServicoEmprestimos servicoEmprestimos;

    public TelaRegistrarDevolucao(TelaManager telaManager, Scanner input, ServicoUsuarios servicoUsuarios, ServicoEmprestimos servicoEmprestimos) {
        super(telaManager, input);
        this.servicoUsuarios = servicoUsuarios;
        this.servicoEmprestimos = servicoEmprestimos;
    }

    @Override
    protected void executar() {
        System.out.println("REGISTRO DE DEVOLUÇÃO");
        quebraLinha();

        solicitarUsuario();
    }

    private void solicitarUsuario() {
        System.out.print("Digite o CPF ou matrícula do usuário, ou 'voltar' para retornar oo menu: ");
        String chaveDeBusca = input.nextLine();

        if(chaveDeBusca.equalsIgnoreCase("voltar")) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Usuario> usuarioOpt = servicoUsuarios.buscarUsuarioPorCpfOuMatricula(chaveDeBusca);

        if(usuarioOpt.isEmpty()) {
            System.out.println("Usuário não encontrado.");
            quebraLinha();
            solicitarUsuario();
            return;
        }

        Usuario usuario = usuarioOpt.get();

        solicitarEmprestimo(usuario);
    }

    private void solicitarEmprestimo(Usuario usuario) {
        List<Emprestimo> emprestimosList = servicoEmprestimos.buscarEmprestimosPara(usuario);

        if (emprestimosList.isEmpty()) {
            System.out.println("Esse usuário não contém emprestimos ativos!");
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

    private void iniciarSelecaoEmprestimo(List<Emprestimo> emprestimosList) {
        quebraLinha();
        System.out.println("Digite o ID do livro a ser devolvido ou 'voltar' para retornar ao menu");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int escolha = componenteInputNumero.receberNumero();

        if(escolha == -1) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Emprestimo> emprestimoSelecionadoOpt = emprestimosList.stream().filter(emprestimo -> emprestimo.getLivro().getId() == escolha).findFirst();

        if(emprestimoSelecionadoOpt.isEmpty()) {
            System.out.println("Livro não encontrado");
            iniciarSelecaoEmprestimo(emprestimosList);

            return;
        }

        Emprestimo emprestimoSelecionado = emprestimoSelecionadoOpt.get();

        realizarDevolucao(emprestimoSelecionado.getId());
    }

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
        if(valorMulta != null) {
            quebraLinha();
            System.out.println("Houve um atraso na devolução.");
            System.out.printf("Valor da multa: R$ %.2f %n", valorMulta);
        }

        quebraLinha();
        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }
}

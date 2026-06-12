package ui.screen.impl;

import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import services.ServicoEmprestimos;
import services.ServicoLivros;
import services.ServicoUsuarios;
import ui.components.ComponenteInputNumero;
import ui.screen.Tela;
import ui.screen.TelaEnum;
import ui.screen.TelaManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Tela de interface responsável pelo fluxo de registro de um novo empréstimo.
 * Orquestra a identificação do usuário, a seleção do livro no acervo,
 * a validação das regras de negócio e a confirmação da operação.
 */
public class TelaRegistroDeEmprestimo extends Tela {

    private final ServicoEmprestimos servicoEmprestimos;
    private final ServicoLivros servicoLivros;
    private final ServicoUsuarios servicoUsuarios;

    /**
     * Constrói a tela de registro de empréstimo.
     *
     * @param telaManager        o gerenciador de navegação entre telas.
     * @param input              o {@link Scanner} para entrada de dados.
     * @param servicoEmprestimos o serviço para realizar o registro do empréstimo.
     * @param servicoLivros      o serviço para buscar e validar a disponibilidade de livros.
     * @param servicoUsuarios    o serviço para localizar o usuário.
     */
    public TelaRegistroDeEmprestimo(TelaManager telaManager, Scanner input, ServicoEmprestimos servicoEmprestimos, ServicoLivros servicoLivros, ServicoUsuarios servicoUsuarios) {
        super(telaManager, input);
        this.servicoEmprestimos = servicoEmprestimos;
        this.servicoLivros = servicoLivros;
        this.servicoUsuarios = servicoUsuarios;
    }

    /**
     * Executa o fluxo inicial da tela de registro de empréstimo.
     */
    @Override
    protected void executar() {
        System.out.println("REGISTRO DE EMPRÉSTIMO");
        quebraLinha();

        solicitarUsuario();
    }

    /**
     * Solicita o CPF ou matrícula do usuário. Permite o retorno ao menu principal.
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
        solicitarLivro(usuario);
    }

    /**
     * Solicita o ID do livro a ser emprestado e valida sua existência no acervo.
     *
     * @param usuario o usuário que receberá o empréstimo.
     */
    private void solicitarLivro(Usuario usuario) {
        System.out.print("Digite o ID do livro ou 'voltar' para retornar ao menu: ");
        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int idSelecionado = componenteInputNumero.receberNumero();

        if (idSelecionado == -1) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Livro> livroOpt = servicoLivros.buscarPorId(idSelecionado);

        if (livroOpt.isEmpty()) {
            System.out.println("Livro não encontrado.");
            quebraLinha();
            solicitarLivro(usuario);
            return;
        }

        Livro livro = livroOpt.get();
        realizarEmprestimo(usuario, livro);
    }

    /**
     * Realiza a validação das regras de negócio (disponibilidade e limite de empréstimos)
     * e registra o empréstimo no sistema.
     *
     * @param usuario o usuário alvo.
     * @param livro   o livro a ser emprestado.
     */
    private void realizarEmprestimo(Usuario usuario, Livro livro) {
        if (!livro.verificaDisponivel()) {
            System.out.println("Impossível realizar o empréstimo porque o livro não está disponível");
            quebraLinha();
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        if (!usuario.verificaLimiteEmprestimos()) {
            System.out.println("Impossível realizar o empréstimo porque o usuário já atingiu o limite de empréstimos");
            quebraLinha();
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Emprestimo> emprestimoRealizado = servicoEmprestimos.realizarEmprestimo(usuario, livro);

        if (emprestimoRealizado.isEmpty()) {
            System.out.println("Erro inesperado ao realizar o empréstimo");
        } else {
            Emprestimo emprestimo = emprestimoRealizado.get();
            Date limiteDevolucao = emprestimo.dataLimiteDevolucao();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            String dataFormatada = formato.format(limiteDevolucao);

            System.out.println("Empréstimo realizado com sucesso!");
            System.out.println("Data prevista de devolução: " + dataFormatada);
        }

        quebraLinha();
        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }
}
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
 * Tela onde o usuário faz o empréstimo de um livro da biblioteca.
 * Ela garante que o usuário e o livro existem no sistema antes de salvar o empréstimo.
 */
public class TelaRegistroDeEmprestimo extends Tela {
    private final ServicoEmprestimos servicoEmprestimos;
    private final ServicoLivros servicoLivros;
    private final ServicoUsuarios servicoUsuarios;

    /**
     * Construtor da tela de empréstimos.
     * @param telaManager        Controlador das telas do sistema.
     * @param input              O Scanner para ler as opções do teclado.
     * @param servicoEmprestimos Serviço que salva o empréstimo.
     * @param servicoLivros      Serviço para buscar o livro no acervo.
     * @param servicoUsuarios    Serviço para buscar os dados do usuário.
     */
    public TelaRegistroDeEmprestimo(TelaManager telaManager, Scanner input, ServicoEmprestimos servicoEmprestimos, ServicoLivros servicoLivros, ServicoUsuarios servicoUsuarios) {
        super(telaManager, input);
        this.servicoEmprestimos = servicoEmprestimos;
        this.servicoLivros = servicoLivros;
        this.servicoUsuarios = servicoUsuarios;
    }

    /**
     * {@inheritDoc}
     * Abre a tela pedindo para identificar qual usuário quer pegar um livro.
     */
    @Override
    protected void executar() {
        System.out.println("REGISTRO DE EMPRÉSTIMO");
        quebraLinha();

        solicitarUsuario();
    }

    /**
     * Fica pedindo o CPF ou matrícula até encontrar o usuário no banco de dados.
     * Também permite digitar 'voltar' para sair da tela.
     */
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

        solicitarLivro(usuario);
    }

    /**
     * Pede o ID do livro que o usuário quer pegar emprestado.
     * @param usuario O usuário que vai receber o empréstimo.
     */
    private void solicitarLivro(Usuario usuario) {
        System.out.print("Digite o ID do livro ou 'voltar' para retornar oo menu: ");
        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int idSelecionado = componenteInputNumero.receberNumero();

        if(idSelecionado == -1) {
            trocarTela(TelaEnum.MENU_PRINCIPAL);
            return;
        }

        Optional<Livro> livroOpt = servicoLivros.buscarPorId(idSelecionado);

        if(livroOpt.isEmpty()) {
            System.out.println("Livro não encontrado.");
            quebraLinha();
            solicitarLivro(usuario);
            return;
        }

        Livro livro = livroOpt.get();

        realizarEmprestimo(usuario, livro);
    }

    /**
     * Tenta salvar o empréstimo no banco de dados.
     * Se tudo der certo, mostra na tela a data limite que o livro precisa ser devolvido.
     * @param usurio O usuário que pegou o livro.
     * @param livro  O livro que está saindo da biblioteca.
     */
    private void realizarEmprestimo(Usuario usurio, Livro livro) {
        Optional<Emprestimo> emprestimoRealizado = servicoEmprestimos.realizarEmprestimo(usurio, livro);

        if(emprestimoRealizado.isEmpty()) {
            System.out.println("Não foi possível realizar o empréstimo");
        }
        else {
            Emprestimo emprestimo = emprestimoRealizado.get();
            Date limiteDevolucao = emprestimo.dataLimiteDevolucao();
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            String dataFormatada = formato.format(limiteDevolucao);

            System.out.println("Empréstimo realizado com sucesso!");
            System.out.println("Data presvista de devolução: " + dataFormatada);
        }

        quebraLinha();
        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }
}
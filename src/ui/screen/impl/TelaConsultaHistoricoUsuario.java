package ui.screen.impl;

import domain.Emprestimo;
import domain.Usuario;
import services.ServicoEmprestimos;
import services.ServicoUsuarios;
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
 * Tela de interface responsável pela consulta do histórico de empréstimos de um usuário específico.
 * Permite localizar o usuário pelo CPF ou matrícula e exibe todos os seus registros de
 * empréstimos (ativos ou encerrados) através do {@link ComponenteVisualizacaoBlocos}.
 */
public class TelaConsultaHistoricoUsuario extends Tela {

    private final ServicoEmprestimos servicoEmprestimos;
    private final ServicoUsuarios servicoUsuarios;

    /**
     * Constrói a tela de consulta de histórico.
     *
     * @param telaManager        o gerenciador de navegação entre telas.
     * @param input              o {@link Scanner} para entrada de dados.
     * @param servicoEmprestimos o serviço para buscar o histórico de empréstimos.
     * @param servicoUsuarios    o serviço para localizar o usuário.
     */
    public TelaConsultaHistoricoUsuario(TelaManager telaManager, Scanner input, ServicoEmprestimos servicoEmprestimos, ServicoUsuarios servicoUsuarios) {
        super(telaManager, input);
        this.servicoEmprestimos = servicoEmprestimos;
        this.servicoUsuarios = servicoUsuarios;
    }

    /**
     * Executa o fluxo inicial da tela, exibindo o cabeçalho e solicitando os dados do usuário.
     */
    @Override
    protected void executar() {
        System.out.println("CONSULTA HISTÓRICO POR USUÁRIO");
        quebraLinha();
        receberUsuario();
    }

    /**
     * Solicita o CPF ou matrícula do usuário. Permite o retorno ao menu principal.
     */
    private void receberUsuario() {
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
            receberUsuario();
            return;
        }

        Usuario usuario = usuarioOpt.get();
        mostrarEmprestimos(usuario);
    }

    /**
     * Recupera e exibe todos os empréstimos vinculados ao usuário informado.
     *
     * @param usuario o usuário cujo histórico será exibido.
     */
    private void mostrarEmprestimos(Usuario usuario) {
        List<Emprestimo> emprestimoList = servicoEmprestimos.buscarEmprestimosPara(usuario, false);

        ComponenteVisualizacaoBlocos componenteVisualizacaoBlocos = new ComponenteVisualizacaoBlocos(
                List.of(
                        "RETIRADA",
                        "ID LIVRO",
                        "TÍTULO",
                        "AUTOR",
                        "DEVOLUÇÃO",
                        "MULTA"
                )
        );

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Emprestimo emprestimo : emprestimoList) {
            componenteVisualizacaoBlocos.adicionarItem(emprestimo.getId(), List.of(
                    formato.format(emprestimo.getDataRetirada()),
                    String.valueOf(emprestimo.getLivro().getId()),
                    emprestimo.getLivro().getTitulo(),
                    emprestimo.getLivro().getAutor(),
                    emprestimo.getDataDevolucao() != null ? formato.format(emprestimo.getDataDevolucao()) : "-",
                    emprestimo.getValorMulta() != null ? String.format("R$ %.2f", emprestimo.getValorMulta()) : "-"
            ));
        }

        componenteVisualizacaoBlocos.mostrar();

        receberUsuario();
    }
}
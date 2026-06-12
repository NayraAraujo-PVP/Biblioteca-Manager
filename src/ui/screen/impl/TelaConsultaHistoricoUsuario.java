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

public class TelaConsultaHistoricoUsuario extends Tela {
    private final ServicoEmprestimos servicoEmprestimos;
    private final ServicoUsuarios servicoUsuarios;

    public TelaConsultaHistoricoUsuario(TelaManager telaManager, Scanner input, ServicoEmprestimos servicoEmprestimos, ServicoUsuarios servicoUsuarios) {
        super(telaManager, input);
        this.servicoEmprestimos = servicoEmprestimos;
        this.servicoUsuarios = servicoUsuarios;
    }

    @Override
    protected void executar() {
        System.out.println("CONSULTA HISTÓRICO POR USUÁRIO");
        quebraLinha();
        receberUsuario();
    }

    private void receberUsuario() {
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
            receberUsuario();
            return;
        }

        Usuario usuario = usuarioOpt.get();

        mostrarEmprestimos(usuario);
    }

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

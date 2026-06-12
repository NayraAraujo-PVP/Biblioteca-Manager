package ui.screen.impl;

import domain.enums.TitulacaoAcademica;
import services.ServicoUsuarios;
import ui.components.ComponenteEscolha;
import ui.screen.Tela;
import ui.screen.TelaManager;

import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Tela de interface responsável pelo fluxo de cadastro de novos usuários no sistema.
 * Gerencia a seleção do tipo de usuário (Aluno ou Docente) e orquestra a coleta
 * dos dados específicos necessários para cada perfil através do {@link ServicoUsuarios}.
 */
public class TelaCadastrarUsuario extends Tela {

    private final ServicoUsuarios servicoUsuarios;

    /**
     * Constrói a tela de cadastro de usuários.
     *
     * @param telaManager    o gerenciador de navegação entre telas.
     * @param input          o {@link Scanner} para entrada de dados.
     * @param servicoUsuarios o serviço de usuários utilizado para persistir os cadastros.
     */
    public TelaCadastrarUsuario(TelaManager telaManager, Scanner input, ServicoUsuarios servicoUsuarios) {
        super(telaManager, input);
        this.servicoUsuarios = servicoUsuarios;
    }

    /**
     * Inicia o fluxo de cadastro, apresentando ao usuário a opção de escolher
     * entre cadastrar um Aluno ou um Docente.
     */
    @Override
    protected void executar() {
        System.out.println("Selecione o tipo de usuário:");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Aluno", this::cadastrarAluno);
        componenteEscolha.registrarOpcao("Docente", this::cadastrarDocente);

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Executa o fluxo de coleta de dados para o cadastro de um Aluno.
     */
    private void cadastrarAluno() {
        System.out.println("CADASTRAR ALUNO");
        quebraLinha();

        String cpf = receberCpf();
        if (cpf.trim().isEmpty()) {
            voltarMenu();
            return;
        }

        System.out.print("Nome: ");
        String nome = input.nextLine();

        System.out.print("Matrícula: ");
        String matricula = input.nextLine();

        if (servicoUsuarios.contemMatricula(matricula)) {
            quebraLinha();
            System.out.println("Essa matrícula já está cadastrada");
            quebraLinha();
            voltarMenu();
            return;
        }

        servicoUsuarios.cadastrarAluno(cpf, nome, matricula);

        quebraLinha();
        System.out.println("Aluno cadastrado com sucesso!");
        quebraLinha();

        voltarMenu();
    }

    /**
     * Executa o fluxo de coleta de dados para o cadastro de um Docente.
     */
    private void cadastrarDocente() {
        System.out.println("CADASTRAR DOCENTE");
        quebraLinha();

        String cpf = receberCpf();
        if (cpf.trim().isEmpty()) {
            voltarMenu();
            return;
        }

        System.out.print("Nome: ");
        String nome = input.nextLine();

        System.out.print("Departamento: ");
        String departamento = input.nextLine();

        System.out.println("Selecione a titulação acadêmica:");
        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        for (TitulacaoAcademica titulacaoAcademica : TitulacaoAcademica.values()) {
            componenteEscolha.registrarOpcao(titulacaoAcademica.name(), 
                () -> finalizarCadastroDocente(cpf, nome, departamento, titulacaoAcademica));
        }

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Finaliza o registro do docente após a escolha da titulação.
     *
     * @param cpf                CPF do docente.
     * @param nome               nome do docente.
     * @param departamento       departamento.
     * @param titulacaoAcademica titulação acadêmica escolhida.
     */
    private void finalizarCadastroDocente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica) {
        servicoUsuarios.cadastrarDocente(cpf, nome, departamento, titulacaoAcademica);

        quebraLinha();
        System.out.println("Docente cadastrado com sucesso!");
        quebraLinha();

        voltarMenu();
    }

    /**
     * Solicita e valida o CPF do usuário, verificando se já existe no sistema.
     *
     * @return o CPF informado, ou uma String vazia se inválido/existente.
     */
    private String receberCpf() {
        System.out.print("CPF: ");
        String cpf = input.nextLine();

        if (servicoUsuarios.contemCpf(cpf)) {
            System.out.println("Esse CPF já está cadastrado");
            quebraLinha();
            return "";
        }

        return cpf;
    }
}
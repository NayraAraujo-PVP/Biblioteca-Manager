package ui.screen.impl;

import domain.enums.TitulacaoAcademica;
import services.ServicoUsuarios;
import ui.components.ComponenteEscolha;
import ui.screen.Tela;
import ui.screen.TelaManager;

import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

public class TelaCadastrarUsuario extends Tela {
    private final ServicoUsuarios servicoUsuarios;

    public TelaCadastrarUsuario(TelaManager telaManager, Scanner input, ServicoUsuarios servicoUsuarios) {
        super(telaManager, input);
        this.servicoUsuarios = servicoUsuarios;
    }

    @Override
    protected void executar() {
        System.out.println("Selecione o tipo de usuário:");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Aluno", this::cadastrarAluno);
        componenteEscolha.registrarOpcao("Docente", this::cadastrarDocente);

        componenteEscolha.mostrarOpcoes();
    }

    private void cadastrarAluno() {
        System.out.println("CADASTRAR ALUNO");
        quebraLinha();

        String cpf = receberCpf();
        if(cpf.trim().isEmpty()) {
            voltarMenu();
            return;
        }

        System.out.print("Nome: ");
        String nome = input.nextLine();

        System.out.print("Matrícula: ");
        String matricula = input.nextLine();

        servicoUsuarios.cadastrarAluno(cpf, nome, matricula);

        quebraLinha();
        System.out.println("Aluno cadastrado com sucesso!");
        quebraLinha();

        voltarMenu();
    }

    private void cadastrarDocente() {
        System.out.println("CADASTRAR DOCENTE");
        quebraLinha();

        String cpf = receberCpf();
        if(cpf.trim().isEmpty()) {
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
            componenteEscolha.registrarOpcao(titulacaoAcademica.name(), () -> finalizarCadastroDocente(cpf, nome, departamento, titulacaoAcademica));
        }

        componenteEscolha.mostrarOpcoes();
    }

    private void finalizarCadastroDocente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica){
        servicoUsuarios.cadastrarDocente(cpf, nome, departamento, titulacaoAcademica);

        quebraLinha();
        System.out.println("Docente cadastrado com sucesso!");
        quebraLinha();

        voltarMenu();
    }

    private String receberCpf() {
        System.out.print("CPF: ");
        String cpf = input.nextLine();

        if(servicoUsuarios.contemCpf(cpf)) {
            System.out.println("Esse CPF já está cadastrado");
            quebraLinha();
            return "";
        }

        return cpf;
    }
}

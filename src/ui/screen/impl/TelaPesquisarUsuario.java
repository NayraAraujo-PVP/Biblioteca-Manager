package ui.screen.impl;

import domain.Aluno;
import domain.Docente;
import domain.enums.TitulacaoAcademica;
import java.util.List;
import java.util.Scanner;
import services.ServicoUsuarios;
import static ui.UIUtils.quebraLinha;
import ui.components.ComponenteEscolha;
import ui.components.ComponenteInputNumero;
import ui.components.ComponenteVisualizacaoBlocos;
import ui.screen.Tela;
import ui.screen.TelaManager;

/**
 * Tela responsável pela pesquisa e edição de alunos e docentes.
 */
public class TelaPesquisarUsuario extends Tela {
    private final ServicoUsuarios servicoUsuarios;

    /**
     * Cria a tela de pesquisa de usuários.
     *
     * @param telaManager gerenciador de telas
     * @param input leitor de entrada
     * @param servicoUsuarios serviço de usuários
     */
    public TelaPesquisarUsuario(TelaManager telaManager, Scanner input, ServicoUsuarios servicoUsuarios) {
        super(telaManager, input);
        this.servicoUsuarios = servicoUsuarios;
    }

    /**
     * Exibe o menu de escolha do tipo de usuário.
     */
    @Override
    protected void executar() {
        System.out.println("Selecione o tipo de usuário:");

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Aluno", this::pesquisarAluno);
        componenteEscolha.registrarOpcao("Docente", this::pesquisarDocente);

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Inicia a pesquisa de alunos.
     */
    private void pesquisarAluno() {
        System.out.println("PESQUISAR ALUNO");
        quebraLinha();

        iniciarPesquisaAluno();
    }

    /**
     * Solicita um termo e realiza a busca de alunos.
     */
    private void iniciarPesquisaAluno() {
        System.out.print("Insira seu termo de pesquisa (nome, CPF ou matrícula) ou 'voltar' para retornar ao menu: ");

        String termoPesquisa = input.nextLine();
        if (termoPesquisa.equalsIgnoreCase("voltar")) {
            voltarMenu();
            return;
        }

        List<Aluno> alunos = servicoUsuarios.buscarAlunos(termoPesquisa);

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado!");
            quebraLinha();
            iniciarPesquisaAluno();
        }

        ComponenteVisualizacaoBlocos componenteVisualizacaoBlocos = new ComponenteVisualizacaoBlocos(
                List.of(
                        "NOME",
                        "CPF",
                        "MATRÍCULA",
                        "EMPRÉSTIMOS"));

        for (int i = 0; i < alunos.size(); i++) {
            Aluno aluno = alunos.get(i);

            componenteVisualizacaoBlocos.adicionarItem(
                    i + 1,
                    List.of(
                            aluno.getNome(),
                            aluno.getCpf(),
                            aluno.getMatricula(),
                            String.valueOf(aluno.getEmprestimosAtivos())));
        }

        componenteVisualizacaoBlocos.mostrar();

        iniciarSelecaoAluno(alunos);
    }

    /**
     * Permite selecionar um aluno encontrado na pesquisa.
     *
     * @param alunos lista de alunos encontrados
     */
    private void iniciarSelecaoAluno(List<Aluno> alunos) {
        quebraLinha();
        System.out.println("Digite o ID do aluno desejado ou 'voltar' para pesquisar novamente");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int escolha = componenteInputNumero.receberNumero();

        if(escolha == -1) {
            iniciarPesquisaAluno();
            return;
        }

        if(escolha <= 0 || escolha > alunos.size()) {
            System.out.println("Aluno não encontrado");
            iniciarSelecaoAluno(alunos);

            return;
        }

        Aluno alunoSelecionado = alunos.get(escolha - 1);
        apresentarOpcoesAluno(alunoSelecionado);
    }

    /**
     * Exibe as opções disponíveis para um aluno.
     *
     * @param alunoSelecionado aluno selecionado
     */
    private void apresentarOpcoesAluno(Aluno alunoSelecionado) {
        quebraLinha();
        System.out.printf("O aluno '%s' foi selecionado %n", alunoSelecionado.getNome());

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Editar aluno", () -> editarAluno(alunoSelecionado));
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Edita os dados de um aluno.
     *
     * @param alunoSelecionado aluno a ser editado
     */
    private void editarAluno(Aluno alunoSelecionado) {
        System.out.print("Novo nome [" + alunoSelecionado.getNome() + "]: ");
        String novoNome = input.nextLine();
        if(novoNome.trim().isEmpty()) novoNome = alunoSelecionado.getNome();

        System.out.print("Nova matrícula [" + alunoSelecionado.getMatricula() + "]: ");
        String novaMatricula = input.nextLine();
        if(novaMatricula.trim().isEmpty()) novaMatricula = alunoSelecionado.getMatricula();

        quebraLinha();

        servicoUsuarios.editarAluno(alunoSelecionado.getCpf(), novoNome, novaMatricula);
        voltarMenu();
    }

    /**
     * Inicia a pesquisa de docentes.
     */
    private void pesquisarDocente() {
        System.out.println("PESQUISAR DOCENTE");
        quebraLinha();

        iniciarPesquisaDocente();
    }

    /**
     * Solicita um termo e realiza a busca de docentes.
     */
    private void iniciarPesquisaDocente() {
        System.out.print("Insira seu termo de pesquisa (nome, CPF, departamento ou titulação acadêmica) ou 'voltar' para retornar ao menu: ");

        String termoPesquisa = input.nextLine();
        if (termoPesquisa.equalsIgnoreCase("voltar")) {
            voltarMenu();
            return;
        }

        List<Docente> docentes = servicoUsuarios.buscarDocentes(termoPesquisa);

        if (docentes.isEmpty()) {
            System.out.println("Nenhum docente encontrado!");
            quebraLinha();
            iniciarPesquisaDocente();
        }

        ComponenteVisualizacaoBlocos componenteVisualizacaoBlocos = new ComponenteVisualizacaoBlocos(
                List.of(
                        "NOME",
                        "CPF",
                        "DEPARTAMENTO",
                        "TITULAÇÃO",
                        "EMPRÉSTIMOS"));

        for (int i = 0; i < docentes.size(); i++) {
            Docente docente = docentes.get(i);

            componenteVisualizacaoBlocos.adicionarItem(
                    i + 1,
                    List.of(
                            docente.getNome(),
                            docente.getCpf(),
                            docente.getDepartamento(),
                            docente.getTitulacaoAcademica().name(),
                            String.valueOf(docente.getEmprestimosAtivos())));
        }

        componenteVisualizacaoBlocos.mostrar();

        iniciarSelecaoDocente(docentes);
    }

    /**
     * Permite selecionar um docente encontrado.
     *
     * @param docentes lista de docentes encontrados
     */
    private void iniciarSelecaoDocente(List<Docente> docentes) {
        quebraLinha();
        System.out.println("Digite o ID do docente desejado ou 'voltar' para pesquisar novamente");

        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        componenteInputNumero.registrarPseudonimo("voltar", -1);

        int escolha = componenteInputNumero.receberNumero();

        if(escolha == -1) {
            iniciarPesquisaDocente();
            return;
        }

        if(escolha <= 0 || escolha > docentes.size()) {
            System.out.println("Docente não encontrado");
            iniciarSelecaoDocente(docentes);

            return;
        }

        Docente docenteSelecionado = docentes.get(escolha - 1);
        apresentarOpcoesDocente(docenteSelecionado);
    }

    /**
     * Exibe as opções disponíveis para um docente.
     *
     * @param docenteSelecionado docente selecionado
     */
    private void apresentarOpcoesDocente(Docente docenteSelecionado) {
        quebraLinha();
        System.out.printf("O docente '%s' foi selecionado %n", docenteSelecionado.getNome());

        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        componenteEscolha.registrarOpcao("Editar docente", () -> editarDocente(docenteSelecionado));
        componenteEscolha.registrarOpcao("Voltar", this::voltarMenu);

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Edita os dados de um docente.
     *
     * @param docenteSelecionado docente a ser editado
     */
    private void editarDocente(Docente docenteSelecionado) {
        System.out.print("Novo nome [" + docenteSelecionado.getNome() + "]: ");
        String novoNome = input.nextLine();
        if(novoNome.trim().isEmpty()) novoNome = docenteSelecionado.getNome();

        System.out.print("Novo departamento [" + docenteSelecionado.getDepartamento() + "]: ");
        String novoDepartamento = input.nextLine();
        if(novoDepartamento.trim().isEmpty()) novoDepartamento = docenteSelecionado.getDepartamento();

        System.out.print("Nova titulação acadêmica [" + docenteSelecionado.getTitulacaoAcademica() + "]: ");
        ComponenteEscolha componenteEscolha = new ComponenteEscolha(input);

        for (TitulacaoAcademica titulacaoAcademica : TitulacaoAcademica.values()) {
            String finalNovoNome = novoNome;
            String finalNovoDepartamento = novoDepartamento;
            componenteEscolha.registrarOpcao(titulacaoAcademica.name(), () -> finalizarEdicaoDocente(docenteSelecionado.getCpf(), finalNovoNome, finalNovoDepartamento, titulacaoAcademica));
        }

        componenteEscolha.mostrarOpcoes();
    }

    /**
     * Finaliza a edição de um docente.
     *
     * @param cpf CPF do docente
     * @param novoNome novo nome
     * @param novoDepartamento novo departamento
     * @param novaTitulacaoAcademica nova titulação acadêmica
     */
    private void finalizarEdicaoDocente(String cpf, String novoNome, String novoDepartamento, TitulacaoAcademica novaTitulacaoAcademica) {
        quebraLinha();

        servicoUsuarios.editarDocente(cpf, novoNome, novoDepartamento, novaTitulacaoAcademica);
        voltarMenu();
    }

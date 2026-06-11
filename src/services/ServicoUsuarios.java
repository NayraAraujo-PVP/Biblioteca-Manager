package services;

import domain.Aluno;
import domain.Docente;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;
import java.util.List;
import java.util.Optional;

import repository.RepositorioUsuarios;

/**
 * Serviço responsável pelo gerenciamento dos usuários
 * da biblioteca.
 */
public class ServicoUsuarios {

    private final RepositorioUsuarios repositorioUsuarios;

    /**
     * Cria uma instância do serviço de usuários.
     *
     * @param repositorioUsuarios repositório de usuários.
     */
    public ServicoUsuarios(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    /**
     * Verifica se existe um usuário com o CPF informado.
     *
     * @param cpf CPF do usuário.
     * @return true se o CPF estiver cadastrado.
     */
    public boolean contemCpf(String cpf) {
        return repositorioUsuarios.contemCpf(cpf);
    }

    /**
     * Busca alunos pelo termo informado.
     *
     * @param termoPesquisa termo utilizado na pesquisa.
     * @return lista de alunos encontrados.
     */
    public List<Aluno> buscarAlunos(String termoPesquisa) {
        return repositorioUsuarios.buscarAlunos(termoPesquisa);
    }

    /**
     * Busca docentes pelo termo informado.
     *
     * @param termoPesquisa termo utilizado na pesquisa.
     * @return lista de docentes encontrados.
     */
    public List<Docente> buscarDocentes(String termoPesquisa) {
        return repositorioUsuarios.buscarDocentes(termoPesquisa);
    }

    /**
     * Busca um usuário pelo CPF ou matrícula.
     *
     * @param cpfOuMatricula CPF ou matrícula do usuário.
     * @return usuário encontrado, caso exista.
     */
    public Optional<Usuario> buscarUsuarioPorCpfOuMatricula(String cpfOuMatricula) {
        return repositorioUsuarios.buscarUsuarioPorCpfOuMatricula(cpfOuMatricula);
    }

    /**
     * Cadastra um novo aluno.
     *
     * @param cpf CPF do aluno.
     * @param nome nome do aluno.
     * @param matricula matrícula do aluno.
     */
    public void cadastrarAluno(String cpf, String nome, String matricula) {
        if (contemCpf(cpf)) return;

        Aluno aluno = new Aluno(cpf, nome, matricula);

        repositorioUsuarios.salvar(aluno);
    }

    /**
     * Cadastra um novo docente.
     *
     * @param cpf CPF do docente.
     * @param nome nome do docente.
     * @param departamento departamento do docente.
     * @param titulacaoAcademica titulação acadêmica do docente.
     */
    public void cadastrarDocente(String cpf, String nome,
                                 String departamento,
                                 TitulacaoAcademica titulacaoAcademica) {

        if (contemCpf(cpf)) return;

        Docente docente = new Docente(cpf, nome, departamento, titulacaoAcademica);

        repositorioUsuarios.salvar(docente);
    }

    /**
     * Atualiza os dados de um aluno.
     *
     * @param cpf CPF do aluno.
     * @param nome novo nome.
     * @param matricula nova matrícula.
     */
    public void editarAluno(String cpf, String nome, String matricula) {
        if (!repositorioUsuarios.contemCpf(cpf)) return;

        Aluno aluno = repositorioUsuarios.buscarAluno(cpf);

        aluno.setMatricula(matricula);
        aluno.setNome(nome);

        repositorioUsuarios.salvar(aluno);
    }

    /**
     * Atualiza os dados de um docente.
     *
     * @param cpf CPF do docente.
     * @param nome novo nome.
     * @param departamento novo departamento.
     * @param titulacaoAcademica nova titulação acadêmica.
     */
    public void editarDocente(String cpf, String nome,
                              String departamento,
                              TitulacaoAcademica titulacaoAcademica) {

        if (!repositorioUsuarios.contemCpf(cpf)) return;

        Docente docente = repositorioUsuarios.buscarDocente(cpf);

        docente.setNome(nome);
        docente.setDepartamento(departamento);
        docente.setTitulacaoAcademica(titulacaoAcademica);

        repositorioUsuarios.salvar(docente);
    }
}

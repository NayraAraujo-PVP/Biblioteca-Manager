package services;

import domain.Aluno;
import domain.Docente;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;
import java.util.List;
import java.util.Optional;

import repository.RepositorioUsuarios;

/**
 * Serviço responsável pela gestão das regras de negócio relacionadas aos usuários.
 * Coordena as operações de cadastro, edição e busca de alunos e docentes,
 * delegando a persistência ao {@link RepositorioUsuarios}.
 */
public class ServicoUsuarios {

    private final RepositorioUsuarios repositorioUsuarios;

    /**
     * Constrói o serviço com a instância do repositório de usuários necessária.
     *
     * @param repositorioUsuarios repositório de usuários a ser utilizado.
     */
    public ServicoUsuarios(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    /**
     * Verifica se existe um usuário com o CPF informado.
     *
     * @param cpf o CPF a ser verificado.
     * @return true se o CPF estiver cadastrado, false caso contrário.
     */
    public boolean contemCpf(String cpf) {
        return repositorioUsuarios.contemCpf(cpf);
    }

    /**
     * Verifica se existe um aluno com a matrícula informada.
     *
     * @param matricula a matrícula a ser verificada.
     * @return true se a matrícula estiver cadastrada, false caso contrário.
     */
    public boolean contemMatricula(String matricula) {
        return repositorioUsuarios.contemMatricula(matricula);
    }

    /**
     * Busca alunos no sistema conforme o termo de pesquisa fornecido.
     *
     * @param termoPesquisa o texto para filtragem.
     * @return lista de alunos encontrados.
     */
    public List<Aluno> buscarAlunos(String termoPesquisa) {
        return repositorioUsuarios.buscarAlunos(termoPesquisa);
    }

    /**
     * Busca docentes no sistema conforme o termo de pesquisa fornecido.
     *
     * @param termoPesquisa o texto para filtragem.
     * @return lista de docentes encontrados.
     */
    public List<Docente> buscarDocentes(String termoPesquisa) {
        return repositorioUsuarios.buscarDocentes(termoPesquisa);
    }

    /**
     * Busca um usuário genérico utilizando seu CPF ou matrícula.
     *
     * @param cpfOuMatricula o identificador do usuário.
     * @return um {@link Optional} contendo o usuário, se encontrado.
     */
    public Optional<Usuario> buscarUsuarioPorCpfOuMatricula(String cpfOuMatricula) {
        return repositorioUsuarios.buscarUsuarioPorCpfOuMatricula(cpfOuMatricula);
    }

    /**
     * Cadastra um novo aluno no sistema, validando a unicidade de CPF e matrícula.
     *
     * @param cpf       CPF do aluno.
     * @param nome      nome do aluno.
     * @param matricula matrícula do aluno.
     */
    public void cadastrarAluno(String cpf, String nome, String matricula) {
        if(contemCpf(cpf)) return;
        if(contemMatricula(matricula)) return;

        Aluno aluno = new Aluno(cpf, nome, matricula);

        repositorioUsuarios.salvar(aluno);
    }

    /**
     * Cadastra um novo docente no sistema, validando a unicidade do CPF.
     *
     * @param cpf                CPF do docente.
     * @param nome               nome do docente.
     * @param departamento       departamento de vinculação.
     * @param titulacaoAcademica titulação acadêmica.
     */
    public void cadastrarDocente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica) {
        if(contemCpf(cpf)) return;

        Docente docente = new Docente(cpf, nome, departamento, titulacaoAcademica);

        repositorioUsuarios.salvar(docente);
    }

    /**
     * Edita os dados cadastrais de um aluno existente.
     *
     * @param cpf       CPF do aluno a ser editado.
     * @param nome      novo nome.
     * @param matricula nova matrícula.
     */
    public void editarAluno(String cpf, String nome, String matricula) {
        Optional<Aluno> alunoOpt = repositorioUsuarios.buscarAluno(cpf);

        if(alunoOpt.isEmpty()) return;

        Aluno aluno = alunoOpt.get();

        aluno.setMatricula(matricula);
        aluno.setNome(nome);

        repositorioUsuarios.salvar(aluno);
    }

    /**
     * Edita os dados cadastrais de um docente existente.
     *
     * @param cpf                CPF do docente a ser editado.
     * @param nome               novo nome.
     * @param departamento       novo departamento.
     * @param titulacaoAcademica nova titulação acadêmica.
     */
    public void editarDocente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica) {
        Optional<Docente> docenteOpt = repositorioUsuarios.buscarDocente(cpf);

        if(docenteOpt.isEmpty()) return;

        Docente docente = docenteOpt.get();

        docente.setNome(nome);
        docente.setDepartamento(departamento);
        docente.setTitulacaoAcademica(titulacaoAcademica);

        repositorioUsuarios.salvar(docente);
    }
}
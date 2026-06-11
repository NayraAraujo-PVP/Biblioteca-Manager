package services;

import domain.Aluno;
import domain.Docente;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;
import java.util.List;
import java.util.Optional;

import repository.RepositorioUsuarios;

public class ServicoUsuarios {
    private final RepositorioUsuarios repositorioUsuarios;

    public ServicoUsuarios(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    public boolean contemCpf(String cpf) {
        return repositorioUsuarios.contemCpf(cpf);
    }

    public List<Aluno> buscarAlunos(String termoPesquisa) {
        return repositorioUsuarios.buscarAlunos(termoPesquisa);
    }

    public List<Docente> buscarDocentes(String termoPesquisa) {
        return repositorioUsuarios.buscarDocentes(termoPesquisa);
    }

    public Optional<Usuario> buscarUsuarioPorCpfOuMatricula(String cpfOuMatricula) {
        return repositorioUsuarios.buscarUsuarioPorCpfOuMatricula(cpfOuMatricula);
    }

    public void cadastrarAluno(String cpf, String nome, String matricula) {
        if(contemCpf(cpf)) return;

        Aluno aluno = new Aluno(cpf, nome, matricula);

        repositorioUsuarios.salvar(aluno);
    }

    public void cadastrarDocente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica) {
        if(contemCpf(cpf)) return;

        Docente docente = new Docente(cpf, nome, departamento, titulacaoAcademica);

        repositorioUsuarios.salvar(docente);
    }

    public void editarAluno(String cpf, String nome, String matricula) {
        if(!repositorioUsuarios.contemCpf(cpf)) return;

        Aluno aluno = repositorioUsuarios.buscarAluno(cpf);

        aluno.setMatricula(matricula);
        aluno.setNome(nome);

        repositorioUsuarios.salvar(aluno);
    }

    public void editarDocente(String cpf, String nome, String departamento, TitulacaoAcademica titulacaoAcademica) {
        if(!repositorioUsuarios.contemCpf(cpf)) return;

        Docente docente = repositorioUsuarios.buscarDocente(cpf);

        docente.setNome(nome);
        docente.setDepartamento(departamento);
        docente.setTitulacaoAcademica(titulacaoAcademica);


        repositorioUsuarios.salvar(docente);
    }
}

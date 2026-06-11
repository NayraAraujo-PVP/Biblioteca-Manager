package repository;

import datamanager.DataManager;
import domain.Aluno;
import domain.Docente;
import domain.Usuario;
import entities.EntidadeAluno;
import entities.EntidadeDocente;
import entities.EntidadeUsuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioUsuarios {
    private final Map<String, EntidadeAluno> alunoMap = new HashMap<>();
    private final Map<String, EntidadeDocente> docenteMap = new HashMap<>();
    private final Map<String, EntidadeUsuario> usuarioMap = new HashMap<>();

    private final DataManager dataManager;

    private static final String ALUNOS_FILENAME = "alunos";
    private static final String DOCENTES_FILENAME = "docentes";

    public RepositorioUsuarios(DataManager dataManager) {
        this.dataManager = dataManager;

        List<EntidadeAluno> entidadeAlunoList = dataManager.buscar(ALUNOS_FILENAME, EntidadeAluno.class);
        List<EntidadeDocente> entidadeDocenteList = dataManager.buscar(DOCENTES_FILENAME, EntidadeDocente.class);

        for (EntidadeAluno entidadeAluno : entidadeAlunoList) {
            alunoMap.put(entidadeAluno.getCpf(), entidadeAluno);
            usuarioMap.put(entidadeAluno.getCpf(), entidadeAluno);
        }

        for (EntidadeDocente entidadeDocente : entidadeDocenteList) {
            docenteMap.put(entidadeDocente.getCpf(), entidadeDocente);
            usuarioMap.put(entidadeDocente.getCpf(), entidadeDocente);
        }
    }

    public boolean contemCpf(String cpf) {
        return usuarioMap.containsKey(cpf);
    }

    public List<Aluno> buscarAlunos(String termoPesquisa) {
        return alunoMap.values().stream()
                .filter(entidadeAluno -> entidadeAluno.getCpf().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeAluno.getNome().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeAluno.getMatricula().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeAluno::converterParaAluno)
                .toList();
    }

    public List<Docente> buscarDocentes(String termoPesquisa) {
        return docenteMap.values().stream()
                .filter(entidadeDocente -> entidadeDocente.getCpf().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeDocente.getNome().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeDocente.getDepartamento().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeDocente.getTitulacaoAcademica().name().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeDocente::converterParaDocente)
                .toList();
    }

    public void salvar(Aluno aluno) {
        EntidadeAluno entidadeAluno = EntidadeAluno.converterParaEntidade(aluno);
        alunoMap.put(aluno.getCpf(), entidadeAluno);
        usuarioMap.put(aluno.getCpf(), entidadeAluno);

        dataManager.salvar(ALUNOS_FILENAME, alunoMap.values());
    }

    public void salvar(Docente docente) {
        EntidadeDocente entidadeDocente = EntidadeDocente.converterParaEntidade(docente);
        docenteMap.put(docente.getCpf(), entidadeDocente);
        usuarioMap.put(docente.getCpf(), entidadeDocente);

        dataManager.salvar(DOCENTES_FILENAME, docenteMap.values());
    }

    public void salvar(Usuario usuario) {
        if(usuario instanceof Aluno aluno) salvar(aluno);
        if(usuario instanceof Docente docente) salvar(docente);
    }

    public Aluno buscarAluno(String cpf) {
        return alunoMap.get(cpf).converterParaAluno();
    }

    public Docente buscarDocente(String cpf) {
        return docenteMap.get(cpf).converterParaDocente();
    }

    public Usuario buscarUsuario(String cpf) {
        return usuarioMap.get(cpf).converterParaUsuario();
    }
}

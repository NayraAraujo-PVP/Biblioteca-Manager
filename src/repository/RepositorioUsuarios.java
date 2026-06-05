package repository;

import domain.Aluno;
import domain.Docente;
import domain.Livro;
import domain.Usuario;
import entities.EntidadeAluno;
import entities.EntidadeDocente;
import entities.EntidadeLivro;
import entities.EntidadeUsuario;

import java.util.HashMap;
import java.util.Map;

public class RepositorioUsuarios {
    private final Map<String, EntidadeAluno> alunoMap = new HashMap<>();
    private final Map<String, EntidadeDocente> docenteMap = new HashMap<>();
    private final Map<String, EntidadeUsuario> usuarioMap = new HashMap<>();

    public boolean contemCpf(String cpf) {
        return usuarioMap.containsKey(cpf);
    }

    public void salvar(Aluno aluno) {
        EntidadeAluno entidadeAluno = EntidadeAluno.converterParaEntidade(aluno);
        alunoMap.put(aluno.getCpf(), entidadeAluno);
        usuarioMap.put(aluno.getCpf(), entidadeAluno);
    }

    public void salvar(Docente docente) {
        EntidadeDocente entidadeDocente = EntidadeDocente.converterParaEntidade(docente);
        docenteMap.put(docente.getCpf(), entidadeDocente);
        usuarioMap.put(docente.getCpf(), entidadeDocente);
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

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
import java.util.Optional;

/**
 * Repositório responsável pelo armazenamento e recuperação
 * dos usuários da biblioteca.
 */
public class RepositorioUsuarios {

    private final Map<String, EntidadeAluno> alunoMap = new HashMap<>();
    private final Map<String, EntidadeDocente> docenteMap = new HashMap<>();
    private final Map<String, EntidadeUsuario> usuarioMap = new HashMap<>();

    private final DataManager dataManager;

    private static final String ALUNOS_FILENAME = "alunos";
    private static final String DOCENTES_FILENAME = "docentes";

    /**
     * Inicializa o repositório carregando os usuários salvos.
     *
     * @param dataManager gerenciador de persistência.
     */
    public RepositorioUsuarios(DataManager dataManager) {
        this.dataManager = dataManager;

        List<EntidadeAluno> entidadeAlunoList =
                dataManager.buscar(ALUNOS_FILENAME, EntidadeAluno.class);

        List<EntidadeDocente> entidadeDocenteList =
                dataManager.buscar(DOCENTES_FILENAME, EntidadeDocente.class);

        for (EntidadeAluno entidadeAluno : entidadeAlunoList) {
            alunoMap.put(entidadeAluno.getCpf(), entidadeAluno);
            usuarioMap.put(entidadeAluno.getCpf(), entidadeAluno);
        }

        for (EntidadeDocente entidadeDocente : entidadeDocenteList) {
            docenteMap.put(entidadeDocente.getCpf(), entidadeDocente);
            usuarioMap.put(entidadeDocente.getCpf(), entidadeDocente);
        }
    }

    /**
     * Verifica se existe um usuário com o CPF informado.
     *
     * @param cpf CPF do usuário.
     * @return true se o usuário existir.
     */
    public boolean contemCpf(String cpf) {
        return usuarioMap.containsKey(cpf);
    }

    /**
     * Busca alunos pelo CPF, nome ou matrícula.
     *
     * @param termoPesquisa termo utilizado na pesquisa.
     * @return lista de alunos encontrados.
     */
    public List<Aluno> buscarAlunos(String termoPesquisa) {
        return alunoMap.values().stream()
                .filter(entidadeAluno ->
                        entidadeAluno.getCpf().toLowerCase().contains(termoPesquisa.toLowerCase())
                                || entidadeAluno.getNome().toLowerCase().contains(termoPesquisa.toLowerCase())
                                || entidadeAluno.getMatricula().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeAluno::converterParaAluno)
                .toList();
    }

    /**
     * Busca docentes pelo CPF, nome, departamento ou titulação.
     *
     * @param termoPesquisa termo utilizado na pesquisa.
     * @return lista de docentes encontrados.
     */
    public List<Docente> buscarDocentes(String termoPesquisa) {
        return docenteMap.values().stream()
                .filter(entidadeDocente ->
                        entidadeDocente.getCpf().toLowerCase().contains(termoPesquisa.toLowerCase())
                                || entidadeDocente.getNome().toLowerCase().contains(termoPesquisa.toLowerCase())
                                || entidadeDocente.getDepartamento().toLowerCase().contains(termoPesquisa.toLowerCase())
                                || entidadeDocente.getTitulacaoAcademica().name().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeDocente::converterParaDocente)
                .toList();
    }

    /**
     * Busca um usuário pelo CPF ou matrícula.
     *
     * @param cpfOuMatricula CPF ou matrícula do usuário.
     * @return usuário encontrado, se existir.
     */
    public Optional<Usuario> buscarUsuarioPorCpfOuMatricula(String cpfOuMatricula) {
        if (usuarioMap.containsKey(cpfOuMatricula)) {
            return Optional.of(usuarioMap.get(cpfOuMatricula).converterParaUsuario());
        }

        return alunoMap.values().stream()
                .filter(entidadeAluno ->
                        entidadeAluno.getMatricula().equals(cpfOuMatricula))
                .findFirst()
                .map(EntidadeAluno::converterParaAluno);
    }

    /**
     * Salva um aluno.
     *
     * @param aluno aluno a ser salvo.
     */
    public void salvar(Aluno aluno) {
        EntidadeAluno entidadeAluno = EntidadeAluno.converterParaEntidade(aluno);

        alunoMap.put(aluno.getCpf(), entidadeAluno);
        usuarioMap.put(aluno.getCpf(), entidadeAluno);

        dataManager.salvar(ALUNOS_FILENAME, alunoMap.values());
    }

    /**
     * Salva um docente.
     *
     * @param docente docente a ser salvo.
     */
    public void salvar(Docente docente) {
        EntidadeDocente entidadeDocente = EntidadeDocente.converterParaEntidade(docente);

        docenteMap.put(docente.getCpf(), entidadeDocente);
        usuarioMap.put(docente.getCpf(), entidadeDocente);

        dataManager.salvar(DOCENTES_FILENAME, docenteMap.values());
    }

    /**
     * Salva um usuário.
     *
     * @param usuario usuário a ser salvo.
     */
    public void salvar(Usuario usuario) {
        if (usuario instanceof Aluno aluno) salvar(aluno);
        if (usuario instanceof Docente docente) salvar(docente);
    }

    /**
     * Busca um aluno pelo CPF.
     *
     * @param cpf CPF do aluno.
     * @return aluno encontrado.
     */
    public Aluno buscarAluno(String cpf) {
        return alunoMap.get(cpf).converterParaAluno();
    }

    /**
     * Busca um docente pelo CPF.
     *
     * @param cpf CPF do docente.
     * @return docente encontrado.
     */
    public Docente buscarDocente(String cpf) {
        return docenteMap.get(cpf).converterParaDocente();
    }

    /**
     * Busca um usuário pelo CPF.
     *
     * @param cpf CPF do usuário.
     * @return usuário encontrado.
     */
    public Usuario buscarUsuario(String cpf) {
        return usuarioMap.get(cpf).converterParaUsuario();
    }
}

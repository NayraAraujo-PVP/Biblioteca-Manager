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
 * Repositório responsável pela persistência e recuperação de instâncias de {@link Usuario},
 * englobando tanto {@link Aluno} quanto {@link Docente}. 
 * Gerencia a coordenação entre mapas em memória e o armazenamento em arquivos via {@link DataManager}.
 */
public class RepositorioUsuarios {

    private final Map<String, EntidadeAluno> alunoMap = new HashMap<>();
    private final Map<String, EntidadeDocente> docenteMap = new HashMap<>();
    private final Map<String, EntidadeUsuario> usuarioMap = new HashMap<>();

    private final DataManager dataManager;

    private static final String ALUNOS_FILENAME = "alunos";
    private static final String DOCENTES_FILENAME = "docentes";

    /**
     * Constrói o repositório, carregando todos os dados de alunos e docentes persistidos
     * para o cache em memória e populando o mapa unificado de usuários.
     *
     * @param dataManager instância do gerenciador de dados.
     */
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

    /**
     * Verifica se um usuário com o CPF informado já está registrado.
     *
     * @param cpf o CPF a ser verificado.
     * @return true se o CPF existir no sistema, false caso contrário.
     */
    public boolean contemCpf(String cpf) {
        return usuarioMap.containsKey(cpf);
    }

    /**
     * Verifica se uma matrícula de aluno já está registrada no sistema.
     *
     * @param matricula a matrícula a ser verificada.
     * @return true se a matrícula existir, false caso contrário.
     */
    public boolean contemMatricula(String matricula) {
        return alunoMap.values().stream()
                .anyMatch(entidadeAluno -> entidadeAluno.getMatricula().equals(matricula));
    }

    /**
     * Busca alunos cujos dados (CPF, nome ou matrícula) contenham o termo de pesquisa.
     *
     * @param termoPesquisa o texto utilizado para filtragem.
     * @return lista de alunos encontrados.
     */
    public List<Aluno> buscarAlunos(String termoPesquisa) {
        return alunoMap.values().stream()
                .filter(entidadeAluno -> entidadeAluno.getCpf().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeAluno.getNome().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeAluno.getMatricula().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeAluno::converterParaAluno)
                .toList();
    }

    /**
     * Busca docentes cujos dados (CPF, nome, departamento ou titulação) contenham o termo de pesquisa.
     *
     * @param termoPesquisa o texto utilizado para filtragem.
     * @return lista de docentes encontrados.
     */
    public List<Docente> buscarDocentes(String termoPesquisa) {
        return docenteMap.values().stream()
                .filter(entidadeDocente -> entidadeDocente.getCpf().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeDocente.getNome().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeDocente.getDepartamento().toLowerCase().contains(termoPesquisa.toLowerCase())
                        || entidadeDocente.getTitulacaoAcademica().name().toLowerCase().contains(termoPesquisa.toLowerCase()))
                .map(EntidadeDocente::converterParaDocente)
                .toList();
    }

    /**
     * Busca um usuário genérico pelo CPF ou matrícula.
     *
     * @param cpfOuMatricula o identificador (CPF ou matrícula).
     * @return um {@link Optional} com o usuário encontrado, se existir.
     */
    public Optional<Usuario> buscarUsuarioPorCpfOuMatricula(String cpfOuMatricula) {
        if(usuarioMap.containsKey(cpfOuMatricula)) {
            return Optional.of(usuarioMap.get(cpfOuMatricula).converterParaUsuario());
        }

        return alunoMap.values().stream()
                .filter(entidadeAluno -> entidadeAluno.getMatricula().equals(cpfOuMatricula))
                .findFirst()
                .map(EntidadeAluno::converterParaAluno);
    }

    /**
     * Persiste um aluno no repositório.
     *
     * @param aluno o objeto de domínio {@link Aluno} a ser salvo.
     */
    public void salvar(Aluno aluno) {
        EntidadeAluno entidadeAluno = EntidadeAluno.converterParaEntidade(aluno);
        alunoMap.put(aluno.getCpf(), entidadeAluno);
        usuarioMap.put(aluno.getCpf(), entidadeAluno);

        dataManager.salvar(ALUNOS_FILENAME, alunoMap.values());
    }

    /**
     * Persiste um docente no repositório.
     *
     * @param docente o objeto de domínio {@link Docente} a ser salvo.
     */
    public void salvar(Docente docente) {
        EntidadeDocente entidadeDocente = EntidadeDocente.converterParaEntidade(docente);
        docenteMap.put(docente.getCpf(), entidadeDocente);
        usuarioMap.put(docente.getCpf(), entidadeDocente);

        dataManager.salvar(DOCENTES_FILENAME, docenteMap.values());
    }

    /**
     * Persiste um usuário (Aluno ou Docente) identificando seu tipo dinamicamente.
     *
     * @param usuario o objeto de domínio a ser salvo.
     */
    public void salvar(Usuario usuario) {
        if(usuario instanceof Aluno aluno) salvar(aluno);
        if(usuario instanceof Docente docente) salvar(docente);
    }

    /**
     * Busca um aluno pelo CPF.
     *
     * @param cpf CPF do aluno.
     * @return {@link Optional} com o aluno, se encontrado.
     */
    public Optional<Aluno> buscarAluno(String cpf) {
        return Optional.ofNullable(alunoMap.get(cpf)).map(EntidadeAluno::converterParaAluno);
    }

    /**
     * Busca um docente pelo CPF.
     *
     * @param cpf CPF do docente.
     * @return {@link Optional} com o docente, se encontrado.
     */
    public Optional<Docente> buscarDocente(String cpf) {
        return Optional.ofNullable(docenteMap.get(cpf)).map(EntidadeDocente::converterParaDocente);
    }

    /**
     * Busca um usuário pelo CPF.
     *
     * @param cpf CPF do usuário.
     * @return {@link Optional} com o usuário (polimórfico), se encontrado.
     */
    public Optional<Usuario> buscarUsuario(String cpf) {
        return Optional.ofNullable(usuarioMap.get(cpf)).map(EntidadeUsuario::converterParaUsuario);
    }
}
package entities;

import domain.Docente;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;

/**
 * Representa a entidade de persistência de um docente.
 *
 * Utilizada para armazenar e recuperar dados de docentes
 * nos arquivos da aplicação.
 */
public class EntidadeDocente extends EntidadeUsuario {

    private String departamento;
    private TitulacaoAcademica titulacaoAcademica;

    /**
     * Construtor vazio utilizado na desserialização.
     */
    public EntidadeDocente() {}

    /**
     * Cria uma entidade de docente.
     *
     * @param nome nome do docente.
     * @param cpf CPF do docente.
     * @param emprestimosAtivos quantidade de empréstimos ativos.
     * @param departamento departamento do docente.
     * @param titulacaoAcademica titulação acadêmica do docente.
     */
    protected EntidadeDocente(String nome, String cpf,
                              int emprestimosAtivos,
                              String departamento,
                              TitulacaoAcademica titulacaoAcademica) {
        super(nome, cpf, emprestimosAtivos);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    /**
     * @return departamento do docente.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @return titulação acadêmica do docente.
     */
    public TitulacaoAcademica getTitulacaoAcademica() {
        return titulacaoAcademica;
    }

    /**
     * Converte a entidade para um objeto Docente.
     *
     * @return objeto do tipo Docente.
     */
    public Docente converterParaDocente() {
        return new Docente(
                nome,
                cpf,
                emprestimosAtivos,
                departamento,
                titulacaoAcademica
        );
    }

    /**
     * Converte a entidade para um usuário.
     *
     * @return usuário correspondente.
     */
    @Override
    public Usuario converterParaUsuario() {
        return converterParaDocente();
    }

    /**
     * Converte um docente para sua entidade de persistência.
     *
     * @param docente docente a ser convertido.
     * @return entidade correspondente.
     */
    public static EntidadeDocente converterParaEntidade(Docente docente) {
        return new EntidadeDocente(
                docente.getNome(),
                docente.getCpf(),
                docente.getEmprestimosAtivos(),
                docente.getDepartamento(),
                docente.getTitulacaoAcademica()
        );
    }
}

package entities;

import domain.Docente;
import domain.Usuario;
import domain.enums.TitulacaoAcademica;

/**
 * Representa a entidade de persistência para objetos do tipo {@link Docente}.
 * Gerencia a tradução entre a representação de dados do docente e o respectivo
 * objeto de domínio, permitindo a persistência das informações de departamento 
 * e titulação acadêmica.
 */
public class EntidadeDocente extends EntidadeUsuario {

    /**
     * Departamento de vinculação do docente.
     */
    private String departamento;

    /**
     * Titulação acadêmica do docente.
     */
    private TitulacaoAcademica titulacaoAcademica;

    /**
     * Construtor padrão necessário para frameworks de persistência.
     */
    public EntidadeDocente() {}

    /**
     * Construtor para inicialização dos dados da entidade.
     *
     * @param nome               o nome completo do docente.
     * @param cpf                o CPF do docente.
     * @param emprestimosAtivos  a quantidade de empréstimos ativos.
     * @param departamento       o departamento de vinculação.
     * @param titulacaoAcademica a titulação acadêmica.
     */
    protected EntidadeDocente(String nome, String cpf, int emprestimosAtivos, String departamento, TitulacaoAcademica titulacaoAcademica) {
        super(nome, cpf, emprestimosAtivos);
        this.departamento = departamento;
        this.titulacaoAcademica = titulacaoAcademica;
    }

    /**
     * @return o departamento de vinculação.
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @return a titulação acadêmica do docente.
     */
    public TitulacaoAcademica getTitulacaoAcademica() {
        return titulacaoAcademica;
    }

    /**
     * Converte esta entidade em um objeto de domínio {@link Docente}.
     *
     * @return uma instância de {@link Docente} populada com os dados desta entidade.
     */
    public Docente converterParaDocente() {
        return new Docente(nome, cpf, emprestimosAtivos, departamento, titulacaoAcademica);
    }

    /**
     * Realiza a conversão polimórfica desta entidade para um objeto de domínio do tipo {@link Usuario}.
     *
     * @return uma instância de {@link Usuario} baseada no docente.
     */
    @Override
    public Usuario converterParaUsuario() {
        return converterParaDocente();
    }

    /**
     * Converte um objeto de domínio {@link Docente} em uma entidade de persistência {@link EntidadeDocente}.
     *
     * @param docente o objeto de domínio a ser convertido.
     * @return a entidade correspondente.
     */
    public static EntidadeDocente converterParaEntidade(Docente docente) {
        return new EntidadeDocente(
                docente.getNome(),
                docente.getCpf(),
                docente.getEmprestimosAtivos(),
                docente.getDepartamento(),
                docente.getTitulacaoAcademica());
    }
}
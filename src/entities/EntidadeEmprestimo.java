package entities;

import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import utils.DateUtils;

/**
 * Representa a entidade de persistência para objetos do tipo {@link Emprestimo}.
 * Armazena as referências aos objetos de domínio por meio de identificadores (CPF e ID)
 * e realiza a conversão de datas para o formato de persistência (String).
 */
public class EntidadeEmprestimo {
    private int id;
    private String cpfUsuario;
    private int idLivro;
    private String dataRetirada;
    private String dataDevolucao;
    private Double valorMulta;
    private boolean devolvido;

    /**
     * Construtor padrão necessário para frameworks de persistência.
     */
    public EntidadeEmprestimo() {}

    /**
     * Construtor privado para inicialização dos dados da entidade.
     *
     * @param id            identificador único do empréstimo.
     * @param cpfUsuario    CPF do usuário associado.
     * @param idLivro       ID do livro associado.
     * @param dataRetirada  data de retirada em formato textual.
     * @param dataDevolucao data de devolução em formato textual.
     * @param valorMulta    valor da multa aplicada.
     * @param devolvido     status de devolução do item.
     */
    private EntidadeEmprestimo(int id, String cpfUsuario, int idLivro, String dataRetirada, String dataDevolucao, Double valorMulta, boolean devolvido) {
        this.id = id;
        this.cpfUsuario = cpfUsuario;
        this.idLivro = idLivro;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorMulta = valorMulta;
        this.devolvido = devolvido;
    }

    /**
     * @return o identificador do empréstimo.
     */
    public int getId() {
        return id;
    }

    /**
     * @return o CPF do usuário associado.
     */
    public String getCpfUsuario() {
        return cpfUsuario;
    }

    /**
     * @return o identificador do livro associado.
     */
    public int getIdLivro() {
        return idLivro;
    }

    /**
     * @return a data de retirada em formato de String.
     */
    public String getDataRetirada() {
        return dataRetirada;
    }

    /**
     * @return a data de devolução em formato de String.
     */
    public String getDataDevolucao() {
        return dataDevolucao;
    }

    /**
     * @return o valor da multa aplicada.
     */
    public Double getValorMulta() {
        return valorMulta;
    }

    /**
     * @return true se o livro foi devolvido, false caso contrário.
     */
    public boolean isDevolvido() {
        return devolvido;
    }

    /**
     * Converte esta entidade em um objeto de domínio {@link Emprestimo}, utilizando
     * instâncias de {@link Usuario} e {@link Livro} previamente carregadas.
     *
     * @param usuario o usuário associado ao empréstimo.
     * @param livro   o livro associado ao empréstimo.
     * @return uma instância de {@link Emprestimo} populada.
     */
    public Emprestimo converterParaEmprestimo(Usuario usuario, Livro livro) {
        return new Emprestimo(id,
                usuario,
                livro,
                DateUtils.converterDeString(dataRetirada),
                DateUtils.converterDeString(dataDevolucao),
                valorMulta,
                devolvido);
    }

    /**
     * Converte um objeto de domínio {@link Emprestimo} em uma entidade de persistência {@link EntidadeEmprestimo}.
     *
     * @param emprestimo o objeto de domínio a ser convertido.
     * @return a entidade correspondente.
     */
    public static EntidadeEmprestimo converterParaEntidade(Emprestimo emprestimo) {
        return new EntidadeEmprestimo(
                emprestimo.getId(),
                emprestimo.getUsuario().getCpf(),
                emprestimo.getLivro().getId(),
                DateUtils.converterParaString(emprestimo.getDataRetirada()),
                DateUtils.converterParaString(emprestimo.getDataDevolucao()),
                emprestimo.getValorMulta(),
                emprestimo.isDevolvido()
        );
    }
}
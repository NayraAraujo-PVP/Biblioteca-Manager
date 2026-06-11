package entities;

import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import utils.DateUtils;

/**
 * Representa a entidade de persistência de um empréstimo.
 *
 * Utilizada para armazenar e recuperar dados de empréstimos
 * nos arquivos da aplicação.
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
     * Construtor vazio utilizado na desserialização.
     */
    public EntidadeEmprestimo() {}

    /**
     * Cria uma entidade de empréstimo.
     *
     * @param id identificador do empréstimo.
     * @param cpfUsuario CPF do usuário.
     * @param idLivro identificador do livro.
     * @param dataRetirada data de retirada.
     * @param dataDevolucao data de devolução.
     * @param valorMulta valor da multa.
     * @param devolvido indica se o livro foi devolvido.
     */
    private EntidadeEmprestimo(int id, String cpfUsuario, int idLivro,
                               String dataRetirada, String dataDevolucao,
                               Double valorMulta, boolean devolvido) {
        this.id = id;
        this.cpfUsuario = cpfUsuario;
        this.idLivro = idLivro;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorMulta = valorMulta;
        this.devolvido = devolvido;
    }

    /**
     * @return identificador do empréstimo.
     */
    public int getId() {
        return id;
    }

    /**
     * @return CPF do usuário.
     */
    public String getCpfUsuario() {
        return cpfUsuario;
    }

    /**
     * @return identificador do livro.
     */
    public int getIdLivro() {
        return idLivro;
    }

    /**
     * @return data de retirada.
     */
    public String getDataRetirada() {
        return dataRetirada;
    }

    /**
     * @return data de devolução.
     */
    public String getDataDevolucao() {
        return dataDevolucao;
    }

    /**
     * @return valor da multa.
     */
    public Double getValorMulta() {
        return valorMulta;
    }

    /**
     * @return true se o livro foi devolvido.
     */
    public boolean isDevolvido() {
        return devolvido;
    }

    /**
     * Converte a entidade para um objeto Emprestimo.
     *
     * @param usuario usuário associado ao empréstimo.
     * @param livro livro associado ao empréstimo.
     * @return objeto do tipo Emprestimo.
     */
    public Emprestimo converterParaEmprestimo(Usuario usuario, Livro livro) {
        return new Emprestimo(
                id,
                usuario,
                livro,
                DateUtils.converterDeString(dataRetirada),
                DateUtils.converterDeString(dataDevolucao),
                valorMulta,
                devolvido
        );
    }

    /**
     * Converte um empréstimo para sua entidade de persistência.
     *
     * @param emprestimo empréstimo a ser convertido.
     * @return entidade correspondente.
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

package domain;

import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Representa um empréstimo de livro realizado por um usuário.
 *
 * Armazena informações sobre o livro emprestado, usuário,
 * datas de retirada e devolução, além da multa por atraso.
 */
public class Emprestimo {

    private final int id;
    private final Usuario usuario;
    private final Livro livro;
    private final Date dataRetirada;
    private Date dataDevolucao;
    private Double valorMulta;
    private boolean devolvido;

    /**
     * Cria um novo empréstimo.
     *
     * @param id identificador do empréstimo.
     * @param usuario usuário responsável pelo empréstimo.
     * @param livro livro emprestado.
     * @param dataRetirada data da retirada do livro.
     */
    public Emprestimo(int id, Usuario usuario, Livro livro, Date dataRetirada) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.dataRetirada = dataRetirada;
    }

    /**
     * Cria um empréstimo com todos os dados preenchidos.
     *
     * @param id identificador do empréstimo.
     * @param usuario usuário responsável.
     * @param livro livro emprestado.
     * @param dataRetirada data da retirada.
     * @param dataDevolucao data da devolução.
     * @param valorMulta valor da multa.
     * @param devolvido indica se o livro foi devolvido.
     */
    public Emprestimo(int id, Usuario usuario, Livro livro,
                      Date dataRetirada, Date dataDevolucao,
                      Double valorMulta, boolean devolvido) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
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
     * @return usuário do empréstimo.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @return livro emprestado.
     */
    public Livro getLivro() {
        return livro;
    }

    /**
     * @return data de retirada do livro.
     */
    public Date getDataRetirada() {
        return dataRetirada;
    }

    /**
     * @return data de devolução do livro.
     */
    public Date getDataDevolucao() {
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
     * Registra a devolução do livro e calcula a multa,
     * caso exista atraso.
     *
     * @param dataDevolucao data em que o livro foi devolvido.
     */
    public void registrarDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        devolvido = true;

        int diasPermanenciaLivro = (int) ChronoUnit.DAYS.between(
                dataRetirada.toInstant(),
                dataDevolucao.toInstant()
        );

        int diasDeAtraso =
                usuario.calculaDiasAtraso(diasPermanenciaLivro);

        valorMulta = usuario.calculaMulta(diasDeAtraso);
    }

    /**
     * Calcula a data limite para devolução do livro.
     *
     * @return data limite de devolução.
     */
    public Date dataLimiteDevolucao() {
        int prazoPermanencia = usuario.prazoPermanencia();

        return Date.from(
                dataRetirada.toInstant()
                        .plus(prazoPermanencia, ChronoUnit.DAYS)
        );
    }

    /**
     * Compara empréstimos pelo identificador.
     *
     * @param obj objeto a ser comparado.
     * @return true se os empréstimos possuem o mesmo id.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Emprestimo emprestimo
                && emprestimo.id == this.id;
    }
}

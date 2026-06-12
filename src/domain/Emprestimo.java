package domain;

import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Representa um registro de empréstimo de um livro para um usuário.
 * Gerencia as datas de retirada e devolução, bem como o cálculo de multas 
 * e prazos de permanência baseados no perfil do usuário.
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
     * Construtor para inicialização de um novo empréstimo.
     *
     * @param id           identificador único do empréstimo.
     * @param usuario      o usuário que está realizando o empréstimo.
     * @param livro        o livro a ser emprestado.
     * @param dataRetirada a data de retirada do livro.
     */
    public Emprestimo(int id, Usuario usuario, Livro livro, Date dataRetirada) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.dataRetirada = dataRetirada;
    }

    /**
     * Construtor completo para inicialização de um empréstimo existente.
     *
     * @param id             identificador único do empréstimo.
     * @param usuario        o usuário que realizou o empréstimo.
     * @param livro          o livro emprestado.
     * @param dataRetirada   a data de retirada do livro.
     * @param dataDevolucao  a data em que a devolução ocorreu.
     * @param valorMulta     o valor da multa aplicada, se houver.
     * @param devolvido      status indicando se o livro foi devolvido.
     */
    public Emprestimo(int id, Usuario usuario, Livro livro, Date dataRetirada, Date dataDevolucao, Double valorMulta, boolean devolvido) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
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
     * @return o usuário associado ao empréstimo.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @return o livro emprestado.
     */
    public Livro getLivro() {
        return livro;
    }

    /**
     * @return a data de retirada do livro.
     */
    public Date getDataRetirada() {
        return dataRetirada;
    }

    /**
     * @return a data de devolução, ou null se não houver devolução registrada.
     */
    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    /**
     * @return o valor da multa, ou null se não houver multa aplicada.
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
     * Registra a devolução do livro e realiza o cálculo de eventuais multas
     * com base no prazo estabelecido para o usuário.
     *
     * @param dataDevolucao a data em que o livro foi devolvido.
     */
    public void registrarDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        devolvido = true;

        int diasPermanenciaLivro = (int) ChronoUnit.DAYS.between(dataRetirada.toInstant(), dataDevolucao.toInstant());
        int diasDeAtraso = usuario.calculaDiasAtraso(diasPermanenciaLivro);
        valorMulta = usuario.calculaMulta(diasDeAtraso);
    }

    /**
     * Calcula a data limite para a devolução do livro sem incidência de multa.
     *
     * @return a data limite de devolução.
     */
    public Date dataLimiteDevolucao() {
        int prazoPermanencia = usuario.prazoPermanencia();
        return Date.from(dataRetirada.toInstant().plus(prazoPermanencia, ChronoUnit.DAYS));
    }

    /**
     * Compara este objeto com outro para verificar igualdade baseada no identificador.
     *
     * @param obj o objeto a ser comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Emprestimo emprestimo && emprestimo.id == this.id;
    }
}
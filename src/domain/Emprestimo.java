package domain;

import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Emprestimo {
    private final int id;
    private final Usuario usuario;
    private final Livro livro;
    private final Date dataRetirada;
    private Date dataDevolucao;
    private Double valorMulta;
    private boolean devolvido;

    public Emprestimo(int id, Usuario usuario, Livro livro, Date dataRetirada) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.dataRetirada = dataRetirada;
    }

    public Emprestimo(int id, Usuario usuario, Livro livro, Date dataRetirada, Date dataDevolucao, Double valorMulta, boolean devolvido) {
        this.id = id;
        this.usuario = usuario;
        this.livro = livro;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorMulta = valorMulta;
        this.devolvido = devolvido;
    }

    public int getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public Date getDataRetirada() {
        return dataRetirada;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public Double getValorMulta() {
        return valorMulta;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public void registrarDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
        devolvido = true;

        int diasPermanenciaLivro = (int) ChronoUnit.DAYS.between(dataRetirada.toInstant(), dataDevolucao.toInstant());
        int diasDeAtraso = usuario.calculaDiasAtraso(diasPermanenciaLivro);
        valorMulta = usuario.calculaMulta(diasDeAtraso);
    }

    public Date dataLimiteDevolucao() {
        int prazoPermanencia = usuario.prazoPermanencia();
        return Date.from(dataRetirada.toInstant().plus(prazoPermanencia, ChronoUnit.DAYS));
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Emprestimo emprestimo && emprestimo.id == this.id;
    }
}

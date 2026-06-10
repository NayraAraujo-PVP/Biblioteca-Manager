package entities;

import domain.Emprestimo;
import domain.Livro;
import domain.Usuario;
import utils.DateUtils;

public class EntidadeEmprestimo {
    private int id;
    private String cpfUsuario;
    private int idLivro;
    private String dataRetirada;
    private String dataDevolucao;
    private Double valorMulta;
    private boolean devolvido;

    public EntidadeEmprestimo() {}

    private EntidadeEmprestimo(int id, String cpfUsuario, int idLivro, String dataRetirada, String dataDevolucao, Double valorMulta, boolean devolvido) {
        this.id = id;
        this.cpfUsuario = cpfUsuario;
        this.idLivro = idLivro;
        this.dataRetirada = dataRetirada;
        this.dataDevolucao = dataDevolucao;
        this.valorMulta = valorMulta;
        this.devolvido = devolvido;
    }

    public int getId() {
        return id;
    }

    public String getCpfUsuario() {
        return cpfUsuario;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public String getDataRetirada() {
        return dataRetirada;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public Double getValorMulta() {
        return valorMulta;
    }

    public boolean isDevolvido() {
        return devolvido;
    }

    public Emprestimo converterParaEmprestimo(Usuario usuario, Livro livro) {
        return new Emprestimo(id,
                usuario,
                livro,
                DateUtils.converterDeString(dataRetirada),
                DateUtils.converterDeString(dataDevolucao),
                valorMulta,
                devolvido);
    }

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

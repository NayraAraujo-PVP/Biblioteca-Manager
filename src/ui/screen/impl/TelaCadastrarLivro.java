package ui.screen.impl;

import services.ServicoLivros;
import ui.screen.TelaManager;
import ui.screen.Tela;
import ui.components.ComponenteInputNumero;

import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

/**
 * Tela responsável pelo cadastro de novos livros no sistema.
 * Interage com o usuário para coletar as informações bibliográficas e 
 * aciona o {@link ServicoLivros} para realizar a persistência dos dados.
 */
public class TelaCadastrarLivro extends Tela {

    private final ServicoLivros servicoLivros;

    /**
     * Constrói a tela de cadastro de livros.
     *
     * @param telaManager   o gerenciador responsável pela navegação entre telas.
     * @param input         o {@link Scanner} para captura de entradas do usuário.
     * @param servicoLivros o serviço de livros utilizado para as operações de cadastro.
     */
    public TelaCadastrarLivro(TelaManager telaManager, Scanner input, ServicoLivros servicoLivros) {
        super(telaManager, input);
        this.servicoLivros = servicoLivros;
    }

    /**
     * Executa o fluxo da tela de cadastro. Solicita os dados do livro ao usuário,
     * realiza o processamento através do serviço e retorna ao menu anterior.
     */
    @Override
    protected void executar() {
        System.out.println("CADASTRAR LIVRO");
        quebraLinha();

        System.out.print("Título: ");
        String titulo = input.nextLine();

        System.out.print("Autor: ");
        String autor = input.nextLine();

        System.out.print("Categoria: ");
        String categoria = input.nextLine();

        System.out.print("Quantidade total: ");
        ComponenteInputNumero componenteInputNumero = new ComponenteInputNumero(input);
        int quantidadeTotal = componenteInputNumero.receberNumero();

        servicoLivros.cadastrarLivro(titulo, autor, categoria, quantidadeTotal);

        quebraLinha();
        System.out.println("Livro cadastrado com sucesso!");
        quebraLinha();

        voltarMenu();
    }
}
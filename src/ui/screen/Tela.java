package ui.screen;

import java.util.Scanner;

/**
 * Classe base abstrata para todas as telas da interface no terminal.
 * Ela centraliza as dependências fundamentais, como o gerenciador de navegação (TelaManager) 
 * e o leitor de entrada de dados (Scanner), promovendo o reúso de código e padronizando 
 * a estrutura das subclasses.
 */
public abstract class Tela {
    private final TelaManager telaManager;
    protected final Scanner input;

    /**
     * Construtor da classe base responsável por injetar as dependências comuns a todas as telas.
     * * @param telaManager O gerenciador que controla o fluxo e a troca de telas do sistema.
     * @param input       O leitor de teclado (Scanner) utilizado para capturar as entradas do usuário.
     */
    protected Tela(TelaManager telaManager, Scanner input) {
        this.telaManager = telaManager;
        this.input = input;
    }

    /**
     * Solicita ao gerenciador a substituição da tela ativa no momento pela tela informada.
     * * @param telaEnum O identificador único da próxima tela a ser carregada.
     */
    protected void trocarTela(TelaEnum telaEnum) {
        telaManager.trocarTela(telaEnum);
    }

    /**
     * Método utilitário que simplifica o fluxo de navegação, redirecionando 
     * o usuário diretamente de volta para o Menu Principal.
     */
    protected void voltarMenu() {
        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }

    /**
     * Método público invocado pelo gerenciador para ativar e exibir a tela.
     * Ele atua encapsulando a chamada, acionando a execução da lógica interna definida na subclasse.
     */
    public void abrir() {
        executar();
    }

    /**
     * Método abstrato que concentra a regra de negócio e de apresentação de cada tela específica.
     * Toda classe filha deve obrigatoriamente implementar este método para definir seus menus, 
     * saídas de texto e realizar o processamento das interações do usuário.
     */
    protected abstract void executar();
}
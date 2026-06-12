package ui.screen;

import java.util.Scanner;

/**
 * Classe base abstrata para todas as telas do sistema.
 * Define a estrutura comum para navegação entre telas e a interface de execução 
 * para os componentes de interface do usuário.
 */
public abstract class Tela {
    
    private final TelaManager telaManager;
    protected final Scanner input;

    /**
     * Construtor protegido para inicialização dos dependentes de navegação e entrada.
     *
     * @param telaManager o gerenciador responsável pela troca de telas.
     * @param input       o {@link Scanner} compartilhado para captura de entradas.
     */
    protected Tela(TelaManager telaManager, Scanner input) {
        this.telaManager = telaManager;
        this.input = input;
    }

    /**
     * Realiza a transição para uma nova tela especificada pelo enum.
     *
     * @param telaEnum o identificador da próxima tela a ser exibida.
     */
    protected void trocarTela(TelaEnum telaEnum) {
        telaManager.trocarTela(telaEnum);
    }

    /**
     * Método auxiliar para facilitar o retorno ao menu principal do sistema.
     */
    protected void voltarMenu() {
        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }

    /**
     * Abre a tela atual, disparando o fluxo de execução definido pela implementação concreta.
     */
    public void abrir() {
        executar();
    }

    /**
     * Método abstrato que define o fluxo específico da tela (lógica de interface e interação).
     * Deve ser implementado pelas classes concretas que estendem esta classe.
     */
    protected abstract void executar();
}
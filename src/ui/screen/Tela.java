package ui.screen;

import java.util.Scanner;

public abstract class Tela {
    private final TelaManager telaManager;
    protected final Scanner input;

    protected Tela(TelaManager telaManager, Scanner input) {
        this.telaManager = telaManager;
        this.input = input;
    }

    protected void trocarTela(TelaEnum telaEnum) {
        telaManager.trocarTela(telaEnum);
    }

    protected void voltarMenu() {
        trocarTela(TelaEnum.MENU_PRINCIPAL);
    }

    public void abrir() {
        executar();
    }

    protected abstract void executar();
}

package ui.screen;

import ui.TelaManager;

public abstract class Tela {
    private final TelaManager telaManager;

    protected Tela(TelaManager telaManager) {
        this.telaManager = telaManager;
    }

    protected void trocarTela(TelaEnum telaEnum) {
        telaManager.trocarTela(telaEnum);
    }

    public void abrir() {
        limpar();
        executar();
    }

    protected abstract void limpar();

    protected abstract void executar();
}

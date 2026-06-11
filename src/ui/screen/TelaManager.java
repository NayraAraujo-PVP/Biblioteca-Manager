package ui.screen;

import java.util.HashMap;
import java.util.Map;

public class TelaManager {
    private final Map<TelaEnum, Tela> telaMap = new HashMap<>();

    private Tela telaAtual = null;

    public void registrarTela(TelaEnum telaEnum, Tela tela) {
        telaMap.put(telaEnum, tela);
    }

    public void trocarTela(TelaEnum telaEnum) {
        if(!telaMap.containsKey(telaEnum)) {
            System.out.printf("Erro inesperado: tela %s inexistente %n", telaEnum);
            return;
        }

        telaAtual = telaMap.get(telaEnum);
    }

    public void abrirTelaAtual() {
        telaAtual.abrir();
    }
}

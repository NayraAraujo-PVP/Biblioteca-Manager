package ui;

import ui.screen.Tela;
import ui.screen.TelaEnum;

import java.util.HashMap;
import java.util.Map;

public class TelaManager {
    private final Map<TelaEnum, Tela> telaMap = new HashMap<>();

    public void registrarTela(TelaEnum telaEnum, Tela tela) {
        telaMap.put(telaEnum, tela);
    }

    public void trocarTela(TelaEnum telaEnum) {
        if(!telaMap.containsKey(telaEnum)) {
            System.out.printf("Erro inesperado: tela %s inexistente %n", telaEnum);
            return;
        }

        Tela tela = telaMap.get(telaEnum);
        tela.abrir();
    }
}

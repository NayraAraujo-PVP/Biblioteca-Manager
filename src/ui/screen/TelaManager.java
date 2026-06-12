package ui.screen;

import java.util.HashMap;
import java.util.Map;

/**
 * Gerenciador de navegação de telas do sistema.
 * Armazena as instâncias de {@link Tela} mapeadas por {@link TelaEnum} e 
 * controla a transição e a execução da tela ativa.
 */
public class TelaManager {
    
    /**
     * Mapa que associa cada identificador de tela (enum) à sua respectiva instância.
     */
    private final Map<TelaEnum, Tela> telaMap = new HashMap<>();

    /**
     * Referência para a tela que está atualmente em foco.
     */
    private Tela telaAtual = null;

    /**
     * Registra uma tela no gerenciador, associando-a ao seu identificador.
     *
     * @param telaEnum o identificador da tela.
     * @param tela     a instância da tela a ser registrada.
     */
    public void registrarTela(TelaEnum telaEnum, Tela tela) {
        telaMap.put(telaEnum, tela);
    }

    /**
     * Define a tela atual a ser exibida, realizando a transição após validação da existência.
     *
     * @param telaEnum o identificador da próxima tela.
     */
    public void trocarTela(TelaEnum telaEnum) {
        if (!telaMap.containsKey(telaEnum)) {
            System.out.printf("Erro inesperado: tela %s inexistente %n", telaEnum);
            return;
        }

        telaAtual = telaMap.get(telaEnum);
    }

    /**
     * Abre a tela que está atualmente definida como ativa.
     * Deve ser chamado dentro do loop principal da aplicação.
     */
    public void abrirTelaAtual() {
        telaAtual.abrir();
    }
}
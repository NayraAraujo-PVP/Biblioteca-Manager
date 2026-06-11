package ui.screen;

import java.util.HashMap;
import java.util.Map;

/**
 * Gerenciador responsável pelo controle e fluxo de navegação da interface.
 * Ele armazena as telas do sistema em um mapeamento (Map) e controla qual delas 
 * está ativa para a interação do usuário no momento.
 */
public class TelaManager {
    private final Map<TelaEnum, Tela> telaMap = new HashMap<>();

    private Tela telaAtual = null;

    /**
     * Registra uma tela no sistema, mapeando-a ao seu identificador (enum) correspondente.
     * Isso permite que a tela seja referenciada e ativada posteriormente no fluxo do programa.
     * * @param telaEnum O identificador único da tela.
     * @param tela     A instância da tela a ser armazenada.
     */
    public void registrarTela(TelaEnum telaEnum, Tela tela) {
        telaMap.put(telaEnum, tela);
    }

    /**
     * Atualiza a tela ativa que será exibida para o usuário. 
     * Caso o identificador solicitado não conste no registro, uma mensagem de erro 
     * de integridade é exibida no console e a troca não ocorre.
     * * @param telaEnum O identificador da tela que deve ser carregada e definida como ativa.
     */
    public void trocarTela(TelaEnum telaEnum) {
        if(!telaMap.containsKey(telaEnum)) {
            System.out.printf("Erro inesperado: tela %s inexistente %n", telaEnum);
            return;
        }

        telaAtual = telaMap.get(telaEnum);
    }

    /**
     * Aciona o método de execução da tela que está definida como ativa no momento, 
     * renderizando-a no terminal e iniciando a interação com o usuário.
     */
    public void abrirTelaAtual() {
        telaAtual.abrir();
    }
}
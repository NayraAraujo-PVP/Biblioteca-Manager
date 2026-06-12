package ui.components;

import java.util.*;

import static ui.UIUtils.quebraLinha;

/**
 * Componente de interface responsável por exibir um menu de opções interativo
 * e executar a ação correspondente à escolha do usuário.
 * Suporta seleção de opções via índice numérico ou texto.
 */
public class ComponenteEscolha {

    private final Scanner input;
    private final List<String> opcoes = new ArrayList<>();
    private final Map<String, Runnable> funcoes = new HashMap<>();

    /**
     * Construtor para inicialização do componente de escolha.
     *
     * @param input o {@link Scanner} utilizado para capturar as entradas do usuário.
     */
    public ComponenteEscolha(Scanner input) {
        this.input = input;
    }

    /**
     * Registra uma nova opção no menu, associando-a a uma ação executável.
     *
     * @param opcao  o texto que descreve a opção.
     * @param funcao a ação a ser executada caso esta opção seja selecionada.
     */
    public void registrarOpcao(String opcao, Runnable funcao) {
        if (funcoes.containsKey(opcao)) {
            System.out.printf("Erro inesperado: opção %s já adicionada %n", opcao);
            return;
        }

        opcoes.add(opcao);
        funcoes.put(opcao, funcao);
    }

    /**
     * Exibe o menu de opções disponíveis no console, captura a entrada do usuário
     * e executa a função correspondente. Em caso de escolha inválida, o método
     * recursivamente exibe o menu novamente.
     */
    public void mostrarOpcoes() {
        quebraLinha();

        for (int i = 0; i < opcoes.size(); i++) {
            String opcao = opcoes.get(i);
            System.out.printf("%d. %s %n", i + 1, opcao);
        }

        quebraLinha();

        String escolha = input.nextLine();

        quebraLinha();

        // Tenta executar via correspondência de texto exato
        if (funcoes.containsKey(escolha)) {
            funcoes.get(escolha).run();
            return;
        }

        // Tenta executar via índice numérico
        try {
            int numeroEscolha = Integer.parseInt(escolha) - 1;

            if (numeroEscolha < opcoes.size() && numeroEscolha >= 0) {
                funcoes.get(opcoes.get(numeroEscolha)).run();
                return;
            }
        } catch (Exception _) {
            // Falha na conversão numérica é tratada como escolha inválida
        }

        System.out.println("Escolha inválida");
        mostrarOpcoes();
    }
}
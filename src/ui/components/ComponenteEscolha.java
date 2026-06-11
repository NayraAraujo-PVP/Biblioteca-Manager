package ui.components;

import java.util.*;

import static ui.UIUtils.quebraLinha;

/**
 * Componente responsável por exibir um menu de opções
 * e executar a função associada à opção escolhida.
 */
public class ComponenteEscolha {

    private final Scanner input;
    private final List<String> opcoes = new ArrayList<>();
    private final Map<String, Runnable> funcoes = new HashMap<>();

    /**
     * Cria o componente de escolha.
     *
     * @param input scanner utilizado para receber a entrada do usuário.
     */
    public ComponenteEscolha(Scanner input) {
        this.input = input;
    }

    /**
     * Registra uma nova opção no menu.
     *
     * @param opcao texto da opção.
     * @param funcao ação executada quando a opção for selecionada.
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
     * Exibe as opções disponíveis e executa a opção escolhida.
     * A escolha pode ser feita pelo número ou pelo nome da opção.
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

        if (funcoes.containsKey(escolha)) {
            funcoes.get(escolha).run();
            return;
        }

        try {
            int numeroEscolha = Integer.parseInt(escolha) - 1;

            if (numeroEscolha < opcoes.size() && numeroEscolha >= 0) {
                funcoes.get(opcoes.get(numeroEscolha)).run();
                return;
            }
        } catch (Exception e) {
        }

        System.out.println("Escolha inválida");
        mostrarOpcoes();
    }
}

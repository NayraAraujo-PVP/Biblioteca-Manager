package ui.components;

import java.util.*;

public class ComponenteEscolha {
    private final Scanner input;
    private final List<String> opcoes = new ArrayList<>();
    private final Map<String, Runnable> funcoes = new HashMap<>();

    public ComponenteEscolha(Scanner input) {
        this.input = input;
    }

    public void registrarOpcao(String opcao, Runnable funcao) {
        if(funcoes.containsKey(opcao)) {
            System.out.printf("Erro inesperado: opção %s já adicionada %n", opcao);
            return;
        }

        opcoes.add(opcao);
        funcoes.put(opcao, funcao);
    }

    public void mostrarOpcoes() {
        System.out.println();
        for(int i = 0; i < opcoes.size(); i++) {
            String opcao = opcoes.get(i);
            System.out.printf("%d. %s %n", i + 1, opcao);
        }
        System.out.println();

        String escolha = input.next();
        System.out.println();

        if(funcoes.containsKey(escolha)) {
            funcoes.get(escolha).run();
            return;
        }

        try {
            int numeroEscolha = Integer.parseInt(escolha) - 1;
            if(numeroEscolha < opcoes.size() && numeroEscolha >= 0) {
                funcoes.get(opcoes.get(numeroEscolha)).run();
                return;
            }
        } catch (Exception _) {}

        System.out.println("Escolha inválida");
        mostrarOpcoes();
    }
}

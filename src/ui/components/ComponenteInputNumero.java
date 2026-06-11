package ui.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static ui.UIUtils.quebraLinha;

public class ComponenteInputNumero {
    private final Scanner input;
    private final Map<String, Integer> pseudonimos = new HashMap<>();

    public ComponenteInputNumero(Scanner input) {
        this.input = input;
    }

    public int receberNumero() {
        String valorString = input.nextLine();
        Integer valor = null;

        while (valor == null) {
            if(pseudonimos.containsKey(valorString.toLowerCase())) {
                return pseudonimos.get(valorString.toLowerCase());
            }

            try {
                valor = Integer.parseInt(valorString);
            } catch (Exception _) {
                quebraLinha();
                System.out.println("O valor deve ser numérico.");

                valorString = input.nextLine();
            }
        }

        return valor;
    }

    public void registrarPseudonimo(String pseudonimo, int valor) {
        pseudonimos.put(pseudonimo.toLowerCase(), valor);
    }
}

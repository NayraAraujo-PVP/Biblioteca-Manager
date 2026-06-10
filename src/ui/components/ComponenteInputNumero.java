package ui.components;

import java.util.Scanner;

public class ComponenteInputNumero {
    private final Scanner input;

    public ComponenteInputNumero(Scanner input) {
        this.input = input;
    }

    public int receberNumero() {
        String valorString = input.next();
        Integer valor = null;

        while (valor == null) {
            try {
                valor = Integer.parseInt(valorString);
            } catch (Exception _) {
                System.out.println();
                System.out.println("O valor deve ser numérico.");

                valorString = input.next();
            }
        }

        return valor;
    }
}

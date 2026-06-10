package ui.screen.components;

import java.util.Scanner;

public class ComponenteInputNumero {

    public int receberNumero() {
        Scanner input = new Scanner(System.in);
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
